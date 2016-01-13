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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostItemStepOneBrand.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostItemStepOneBrand#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostItemStepOneBrand extends Fragment {
    ListView listView;
    String category;
    String subcategory;
    public static PostItemStepOneBrand newInstance(String param1, String param2) {
        PostItemStepOneBrand fragment = new PostItemStepOneBrand();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;

    }

    public PostItemStepOneBrand() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = this.getArguments();
        category = extra.getString("category");
        subcategory = extra.getString("subcategory");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_item_step_one_brand, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new SimplifiedSearchResultsAdapter(getActivity(), subcategory));
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
    public void onStart()
    {
        super.onStart();
        ((MainActivity)getActivity()).hidePostIcon();
        ((MainActivity)getActivity()).hideCartIcon();
        ((MainActivity)getActivity()).hideSearchIcon();
        ((MainActivity) getActivity()).hideDrawerIcon();
        ((MainActivity)getActivity()).setActionBarTitle("Choose Brand");
    }
    @Override
    public void onPause(){
        super.onPause();
        ((MainActivity)getActivity()).showPostIcon();
        ((MainActivity)getActivity()).showCartIcon();
        ((MainActivity)getActivity()).showSearchIcon();
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

        ArrayList<String> brand = new ArrayList<>();
        String subcategory;

        Typeface type;
        Context context;

        //constructor method
        public SimplifiedSearchResultsAdapter(Context context, String subcategory) {

            layoutInflater = LayoutInflater.from(context);

            this.subcategory = subcategory;
            if(subcategory.equals("Desktop"))
            {
                brand.add("Accer");
                brand.add("Apple");
                brand.add("Asus");
                brand.add("Dell");
                brand.add("HP");
                brand.add("Lenovo");
                brand.add("Samsung");
                brand.add("Toshiba");
                brand.add("Other");
            }
            else if(subcategory.equals("Laptop"))
            {
                brand.add("Acer");
                brand.add("Apple");
                brand.add("Asus");
                brand.add("Dell");
                brand.add("HP");
                brand.add("Lenovo");
                brand.add("Samsung");
                brand.add("Toshiba");
                brand.add("Other");

            }
            else if(subcategory.equals("Monitor"))
            {
                brand.add("Acer");
                brand.add("AOC");
                brand.add("Apple");
                brand.add("Asus");
                brand.add("BenQ");
                brand.add("Dell");
                brand.add("HP");
                brand.add("Lenovo");
                brand.add("LG");
                brand.add("Maxell");
                brand.add("Samsung");
                brand.add("Sony");
                brand.add("Toshiba");
                brand.add("Other");
            }
            else if(subcategory.equals("Mouse"))
            {
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
            }
            else if(subcategory.equals("Headphone"))
            {
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
            }
            else if(subcategory.equals("TV"))
            {
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
                brand.add("Computers");
            }
            else if(subcategory.equals("Smart Phone"))
            {
                brand.add("Blackberry");
                brand.add("Apple");
                brand.add("Google");
                brand.add("HTC");
                brand.add("LG");
                brand.add("Nokia");
                brand.add("Motorola");
                brand.add("Samsung");
                brand.add("Sony");
                brand.add("Other");
            }


        }

        @Override
        public int getCount() {
            return brand.size();
        }

        @Override
        public Object getItem(int arg0) {
            return brand.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {




            TextView text;

            final String tempText = brand.get(position);


            convertView = layoutInflater.inflate(R.layout.post_item_list_view, null);
            text = (TextView) convertView.findViewById(R.id.product_name);
            RelativeLayout productList = (RelativeLayout) convertView.findViewById(R.id.productList);

            text.setText(tempText);

            productList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();

                    Fragment stepOneSearch = new PostItemStepOneSearch();

                    bundle.putString("category", category);
                    bundle.putString("subcategory",subcategory);
                    bundle.putString("brand", tempText);
                    stepOneSearch.setArguments(bundle);
                    openFragment(stepOneSearch);
                }
            });
            return convertView;
        }
    }

}
