package vtc.room.a101.week8homework3.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import vtc.room.a101.week8homework3.R;
import vtc.room.a101.week8homework3.adapters.PeopleAdapter;
import vtc.room.a101.week8homework3.models.People;

@SuppressLint("ValidFragment")
public class PeopleFragment extends Fragment {

    private boolean isXML = true;

    @SuppressLint("ValidFragment")
    public PeopleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_people, container, false);

        if (isXML) {
            final ReadInXMLThread readInXMLThread = new ReadInXMLThread(getActivity(), view);
            readInXMLThread.start();
        } else {
            final ReadInJsonFileThread readInJsonFileThread = new ReadInJsonFileThread(getActivity(), view);
            readInJsonFileThread.start();
        }
        return view;
    }

    // read XML ---> thread

    public void setXML(final boolean XML) {
        isXML = XML;
    }

    //read json ---> thread

    final class ReadInXMLThread extends Thread {

        private final Context context;
        private final View view;

        ReadInXMLThread(final Context context, final View view) {
            this.context = context;
            this.view = view;
        }

        @Override
        public void run() {
            parseXML();
        }

        private void parseXML() {
            try {
                final XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
                final XmlPullParser parser = parserFactory.newPullParser();
                try (final InputStream inputStream = context.getAssets().open(context.getString(R.string.people_xml));) {
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(inputStream, null);
                    processParsing(parser);
                } catch (final IOException e) {
                    Log.i(context.getString(R.string.tag), context.getString(R.string.file_io_exc));
                }
            } catch (XmlPullParserException ignored) {
                Log.i(context.getString(R.string.tag), context.getString(R.string.xml_exc));
            }

        }

        private void processParsing(final XmlPullParser parser) {
            final List<People> peoples = new ArrayList<>();
            try {
                int evenType = parser.getEventType();
                People currentPeople = null;
                while (evenType != XmlPullParser.END_DOCUMENT) {
                    String eltName = null;
                    switch (evenType) {
                        case XmlPullParser.START_TAG:
                            eltName = parser.getName();
                            if (context.getString(R.string.people).equals(eltName)) {
                                currentPeople = new People();
                                peoples.add(currentPeople);
                            } else if (currentPeople != null) {
                                if (context.getString(R.string.name).equals(eltName)) {
                                    currentPeople.setName(parser.nextText());
                                } else if (context.getString(R.string.surname).equals(eltName)) {
                                    currentPeople.setSurname(parser.nextText());
                                } else if (context.getString(R.string.price).equals(eltName)) {
                                    currentPeople.setPrice(parser.nextText());
                                }
                            }
                            break;
                    }
                    evenType = parser.next();
                }
            } catch (final XmlPullParserException | IOException e) {
                Log.i(context.getString(R.string.tag), context.getString(R.string.xml_exc));
            }

            initUsers(view, peoples);
        }

        //recycler init method
        private void initUsers(final View view, final List<People> peoples) {
            final RecyclerView recyclerPeoples = view.findViewById(R.id.recycler_peoples);
            recyclerPeoples.setHasFixedSize(true);
            recyclerPeoples.setLayoutManager(new LinearLayoutManager(getActivity()));
            final PeopleAdapter peopleAdapter = new PeopleAdapter(getActivity(), peoples);
            recyclerPeoples.setAdapter(peopleAdapter);
        }
    }

    final class ReadInJsonFileThread extends Thread {

        private final Context context;
        private final View view;

        ReadInJsonFileThread(final Context context, final View view) {
            this.context = context;
            this.view = view;
        }

        @Override
        public void run() {
            readJsonFile();
        }

        private void readJsonFile() {
            final List<People> list = new ArrayList<>();
            StringBuffer stringBuffer = null;
            JSONArray jArray = null;
            try (final BufferedReader bf = new BufferedReader(new InputStreamReader(
                    context.getResources().getAssets().open("people.json")));) {
                String line;
                stringBuffer = new StringBuffer();
                while ((line = bf.readLine()) != null) {
                    stringBuffer.append(line);
                }
                jArray = new JSONArray(stringBuffer.toString());
            } catch (final IOException | JSONException e) {
                Log.i(context.getString(R.string.tag), context.getString(R.string.error_when_read_file));
            }

            assert jArray != null;
            for (int i = 0; i < jArray.length(); i++) {
                try {
                    final JSONObject jsonObject = jArray.getJSONObject(i);
                    final String name = jsonObject.getString("name");
                    final String surname = jsonObject.getString("surname");
                    final String price = jsonObject.getString("price");
                    list.add(new People(name, surname, price));
                } catch (final JSONException e) {
                    Log.i(context.getString(R.string.tag), context.getString(R.string.error_when_read_file));
                }
            }
            initUsers(view, list);
        }

        //recycler init method
        private void initUsers(final View view, final List<People> peoples) {
            final RecyclerView recyclerPeoples = view.findViewById(R.id.recycler_peoples);
            recyclerPeoples.setHasFixedSize(true);
            recyclerPeoples.setLayoutManager(new LinearLayoutManager(getActivity()));
            final PeopleAdapter peopleAdapter = new PeopleAdapter(getActivity(), peoples);
            recyclerPeoples.setAdapter(peopleAdapter);
        }

    }
}
