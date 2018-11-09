package experitest.com.touchmenot;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
public class RecordsFragment extends Fragment {


    public RecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root =  inflater.inflate(R.layout.fragment_records, container, false);
        new ReadRecords().execute();
        return  root;
    }

    private class ReadRecords extends AsyncTask<Void, Void, Void> {
        List<RecordItem> recordsList = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            ObjectInputStream ois = null;
            FileInputStream fis = null;
            try {
                fis = getActivity().openFileInput("R");
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ListView recordslistview = getActivity().findViewById(R.id.recordslistfragment);
            RecordsAdaptor adaptor = new RecordsAdaptor(getActivity(), R.layout.recorditem, recordsList);
            recordslistview.setAdapter(adaptor);
        }
    }

    private class RecordsAdaptor extends ArrayAdapter<RecordItem> {

        List<RecordItem> mRecordsList;

        public RecordsAdaptor(Context context, int textviewresid, List<RecordItem> recordsList) {
            super(context, textviewresid, recordsList);
            mRecordsList = recordsList;

        }

        @Override
        public View getView(int position,  View convertView,  ViewGroup parent) {
            if(convertView==null)
                convertView = getLayoutInflater().inflate(R.layout.recorditem, parent, false);
            RecordItem record = mRecordsList.get(position);
            TextView username = convertView.findViewById(R.id.recordusername);
            username.setText("Username: "+record.getmUsername());
            TextView score = convertView.findViewById(R.id.recordscore);
            score.setText("Score: "+record.getmScore());
            TextView date = convertView.findViewById(R.id.recorddate);
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
