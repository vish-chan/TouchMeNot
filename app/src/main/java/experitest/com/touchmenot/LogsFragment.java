package experitest.com.touchmenot;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogsFragment extends Fragment {


    public LogsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_logs, container, false);
        new ReadLogs().execute();
        return root;
    }

    private class ReadLogs extends AsyncTask<Void, Void, Void> {
        List<RecordItem> recordsList = new ArrayList<>();
        List<String> movesList = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            readRecords("R");
            readMoves("M");
            return null;
        }

        void readRecords(String filename) {
            ObjectInputStream ois = null;
            FileInputStream fis = null;
            try {
                fis = getActivity().openFileInput(filename);
                ois = new ObjectInputStream(fis);
                try {
                    recordsList = (ArrayList<RecordItem>) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (
                    IOException e)

            {
                e.printStackTrace();
            } finally
            {
                try {
                    if (ois != null)
                        ois.close();
                    if (fis != null)
                        fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        void readMoves(String filename) {
            ObjectInputStream ois = null;
            FileInputStream fis = null;
            try {
                fis = getActivity().openFileInput(filename);
                ois = new ObjectInputStream(fis);
                try {
                    movesList = (ArrayList<String>) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (
                    IOException e)

            {
                e.printStackTrace();
            } finally
            {
                try {
                    if (ois != null)
                        ois.close();
                    if (fis != null)
                        fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ListView logslistview = getActivity().findViewById(R.id.logslistview);
            LogsAdaptor adaptor = new LogsAdaptor(getActivity(), R.layout.logitem ,recordsList);
            logslistview.setAdapter(adaptor);
            logslistview.setClickable(true);
            logslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    new AlertDialog.Builder(getActivity())
                            .

                                    setTitle("Moves history.")
                            .

                                    setMessage(movesList.get(position))
                            .

                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).

                            show();
                }
            });
        }
    }

    private class LogsAdaptor extends ArrayAdapter<RecordItem> {

       List<RecordItem> mRecordsList;

        public LogsAdaptor(Context context, int textviewresid, List<RecordItem> recordsList) {
            super(context, textviewresid, recordsList);
            mRecordsList = recordsList;

        }

        @Override
        public View getView(int position,  View convertView,  ViewGroup parent) {
            if(convertView==null)
                convertView = getLayoutInflater().inflate(R.layout.logitem, parent, false);
            RecordItem record = mRecordsList.get(position);
            TextView username = convertView.findViewById(R.id.logusername);
            username.setText("Username: "+record.getmUsername());
            TextView score = convertView.findViewById(R.id.logscore);
            score.setText("Score: "+record.getmScore());
            TextView date = convertView.findViewById(R.id.logdate);
            date.setText("Time of Play: "+record.getmDate());
            return convertView;
        }

        @Override
        public RecordItem getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
