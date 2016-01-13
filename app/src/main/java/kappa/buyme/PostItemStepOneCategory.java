package kappa.buyme;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostItemStepOneCategory.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostItemStepOneCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostItemStepOneCategory extends Fragment {

    ListView listView;
    public static PostItemStepOneCategory newInstance(String param1, String param2) {
        PostItemStepOneCategory fragment = new PostItemStepOneCategory();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public PostItemStepOneCategory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_item_step_one_category, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new SimplifiedSearchResultsAdapter(getActivity()));
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        ((MainActivity)getActivity()).hidePostIcon();
        ((MainActivity)getActivity()).hideCartIcon();
        ((MainActivity)getActivity()).hideSearchIcon();
        ((MainActivity)getActivity()).hideDrawerIcon();

        ((MainActivity)getActivity()).setActionBarTitle("Choose Category");
    }
    @Override
    public void onPause(){
        super.onPause();
        ((MainActivity)getActivity()).showPostIcon();
        ((MainActivity)getActivity()).showCartIcon();
        ((MainActivity) getActivity()).showSearchIcon();
        ((MainActivity) getActivity()).showDrawerIcon();
        ((MainActivity)getActivity()).setActionBarTitle("");
    }
    private void openFragment(final Fragment fragment) {

        if(getActivity().getSupportFragmentManager().findFragmentById(R.id.container)==null)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
        else if(getActivity().getSupportFragmentManager().findFragmentById(R.id.container).getClass().equals(fragment.getClass()))
        {

        }
        else
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("my_fragment").commit();
        }

    }
    class SimplifiedSearchResultsAdapter extends BaseAdapter
    {
        private LayoutInflater layoutInflater;

        String[] category= {"Electronics","Vehicles","Books","Home","Personal Care","Clothes","Outdoor","Materials"};
        int count = category.length;
        Typeface type;
        Context context;

        //constructor method
        public SimplifiedSearchResultsAdapter(Context context) {

            layoutInflater = LayoutInflater.from(context);


        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int arg0) {
            return category[arg0];
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            TextView text;

            final String tempText = category[position];


            convertView = layoutInflater.inflate(R.layout.post_item_list_view, null);
            text = (TextView) convertView.findViewById(R.id.product_name);
            RelativeLayout productList = (RelativeLayout) convertView.findViewById(R.id.productList);

            text.setText(tempText);

            productList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();

                    Fragment stepOneSubCat = new PostItemStepOneSubCategory();

                    bundle.putString("category", tempText);
                    stepOneSubCat.setArguments(bundle);
                    openFragment(stepOneSubCat);
                }
            });
            return convertView;
        }
    }


}
