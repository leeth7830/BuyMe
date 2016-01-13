package kappa.buyme;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostItemStepOneSearch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostItemStepOneSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostItemStepOneSearch extends Fragment {
    ListView listView;
    String category;
    String subcategory;
    String brand;
    SearchView searchView;

    ArrayList<Product> productResults = new ArrayList<Product>();
    public static PostItemStepOneSearch newInstance(String param1, String param2) {
        PostItemStepOneSearch fragment = new PostItemStepOneSearch();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;

    }

    public PostItemStepOneSearch() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = this.getArguments();
        category = extra.getString("category");
        subcategory = extra.getString("subcategory");
        brand = extra.getString("brand");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_item_step_one_search, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.requestFocus();
        ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                toggleSoftInput(InputMethodManager.SHOW_FORCED,
                        InputMethodManager.HIDE_IMPLICIT_ONLY);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.length() > 0) {

                    myAsyncTask m= (myAsyncTask) new myAsyncTask().execute(query);

                } else {


                }
                return false;
            }

        });
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
        ((MainActivity)getActivity()).setActionBarTitle("Search for your item");
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
    private void openFragment(final android.support.v4.app.Fragment fragment) {

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
    class myAsyncTask extends AsyncTask<String, Void, String>
    {
        JSONParser jParser;
        JSONArray productList;
        String url=new String();
        String textSearch;
        ProgressDialog pd;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            productList=new JSONArray();
            jParser = new JSONParser();
            //pd= new ProgressDialog(getActivity());
            //pd.setCancelable(false);
            //pd.setMessage("Searching...");
            //pd.getWindow().setGravity(Gravity.CENTER);
            //pd.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            String input = sText[0].replaceAll(" ", "+");
            url="http://jimmykwong.net/search.php?keywords="+brand+"+"+input;
            String returnResult = getProductList(url);
            this.textSearch = sText[0];
            return returnResult;

        }

        public String getProductList(String url)
        {

            Product tempProduct = new Product();
            String matchFound = "N";
            //productResults is an arraylist with all product details for the search criteria
            //productResults.clear();


            try {


                JSONObject json = jParser.getJSONFromUrl(url);

                productList = json.getJSONArray("ProductList");

                //parse date for dateList
                for(int i=0;i<productList.length();i++)
                {
                    tempProduct = new Product();

                    JSONObject obj=productList.getJSONObject(i);

                    // tempProduct.setProductCode(obj.getString("ProductCode"));
                    tempProduct.setProductName(obj.getString("ProductName"));
                    tempProduct.setProductImage(obj.getString("ProductImageURL"));
                    tempProduct.setProductCategory(obj.getString("ProductCatName"));
                    tempProduct.setProductSubCategory(obj.getString("ProductSubCodeName"));

                    //check if this product is already there in productResults, if yes, then don't add it again.
                    matchFound = "N";

                    for (int j=0; j < productResults.size();j++)
                    {

                        if (productResults.get(j).getProductName().equals(tempProduct.getProductName()))
                        {
                            matchFound = "Y";
                        }
                    }

                    if (matchFound == "N"&&category.equals(tempProduct.getProductCategory())&&subcategory.equals(tempProduct.getProductSubCategory()))
                    {
                        productResults.add(tempProduct);
                    }

                }

                return ("OK");

            } catch (Exception e) {
                e.printStackTrace();
                return ("Exception Caught");
            }
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            if(result.equalsIgnoreCase("Exception Caught")) {
                Toast.makeText(getActivity(), "Unable to connect to server, please try later", Toast.LENGTH_LONG).show();

                //pd.dismiss();
            }
            else
            {


                //calling this method to filter the search results from productResults and move them to
                //filteredProductResults
                //filterProductArray(textSearch);
                listView.setAdapter(new SimplifiedSearchResultsAdapter(getActivity(),productResults));

                //pd.dismiss();
            }
        }

    }
    class SimplifiedSearchResultsAdapter extends BaseAdapter
    {
        private LayoutInflater layoutInflater;

        private ArrayList<Product> productDetails=new ArrayList<Product>();
        int count;
        Typeface type;
        Context context;
        //constructor method
        public SimplifiedSearchResultsAdapter(Context context,  ArrayList<Product> product_details) {

            layoutInflater = LayoutInflater.from(context);

            this.productDetails=product_details;
            this.count= product_details.size();
            this.context = context;


        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int arg0) {
            return productDetails.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {




            TextView text;

            final Product tempProduct = productDetails.get(position);


            convertView = layoutInflater.inflate(R.layout.listfour_simplifiedsearchresults, null);
            text = (TextView) convertView.findViewById(R.id.product_name);
            RelativeLayout productList = (RelativeLayout) convertView.findViewById(R.id.productList);

            text.setText(tempProduct.getProductName());

            productList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();

                    bundle.putString("category", category);
                    bundle.putString("subcategory",subcategory);
                    bundle.putString("brand", brand);
                    bundle.putString("name", tempProduct.getProductName());
                    Fragment stepTwo = new PostItemStepTwo();
                    stepTwo.setArguments(bundle);
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    openFragment(stepTwo);
                }
            });
            return convertView;
        }
    }

}
