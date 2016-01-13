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
 * {@link PostItemStepOneSubCategory.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostItemStepOneSubCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostItemStepOneSubCategory extends Fragment {
    ListView listView;
    String category;
    public static PostItemStepOneSubCategory newInstance(String param1, String param2) {
        PostItemStepOneSubCategory fragment = new PostItemStepOneSubCategory();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;

    }

    public PostItemStepOneSubCategory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = this.getArguments();
        category = extra.getString("category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_item_step_one_sub_category, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new SimplifiedSearchResultsAdapter(getActivity(),category));

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
        ((MainActivity)getActivity()).setActionBarTitle("Choose Subcategory");
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

        ArrayList<String> subCategory = new ArrayList<>();
        String category;

        Typeface type;
        Context context;

        //constructor method
        public SimplifiedSearchResultsAdapter(Context context, String category) {

            layoutInflater = LayoutInflater.from(context);

            this.category = category;
            if(category.equals("Electronics"))
            {
                subCategory.add("Desktop");
                subCategory.add("Laptop");
                subCategory.add("Monitor");
                subCategory.add("Mouse");
                subCategory.add("Headphone");
                subCategory.add("TV");
                subCategory.add("Smart Phone");
            }
            else if(category.equals("Vehicles"))
            {
                subCategory.add("Small Car");
                subCategory.add("Midsize Car");
                subCategory.add("Large Car");
                subCategory.add("Luxury Car");
                subCategory.add("Sports Car");
                subCategory.add("Motorcycle");
                subCategory.add("Truck");
                subCategory.add("Bicycle");
            }
            else if(category.equals("Books"))
            {
                subCategory.add("Novels");
                subCategory.add("Textbook");
            }
            else if(category.equals("Home"))
            {
                subCategory.add("Computers");
                subCategory.add("Computers");
                subCategory.add("Computers");
                subCategory.add("Computers");
                subCategory.add("Computers");
                subCategory.add("Computers");
                subCategory.add("Computers");
                subCategory.add("Computers");
                subCategory.add("Computers");
            }


        }

        @Override
        public int getCount() {
            return subCategory.size();
        }

        @Override
        public Object getItem(int arg0) {
            return subCategory.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {




            TextView text;

            final String tempText = subCategory.get(position);


            convertView = layoutInflater.inflate(R.layout.post_item_list_view, null);
            text = (TextView) convertView.findViewById(R.id.product_name);
            RelativeLayout productList = (RelativeLayout) convertView.findViewById(R.id.productList);

            text.setText(tempText);

            productList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();

                    Fragment stepOneBrand = new PostItemStepOneBrand();

                    bundle.putString("category", category);
                    bundle.putString("subcategory",tempText);
                    stepOneBrand.setArguments(bundle);
                    openFragment(stepOneBrand);
                }
            });
            return convertView;
        }
    }

}
