package kappa.buyme;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostItem.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostItem extends Fragment implements AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    TextView textView4;
    SearchView searchView;
    ListView listView;
    int itemtype;
    int itemtype2;
    String typename1;
    String typename2;
    String brand;
    ArrayAdapter adapter2;
    ArrayAdapter adapter3;
    TextView myText;
    RelativeLayout subCategory;
    RelativeLayout brandItem;
    LinearLayout searchLayout;
    CharSequence c;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Product> productResults = new ArrayList<Product>();
    //Based on the search string, only filtered products will be moved here from productResults
    ArrayList<Product> filteredProductResults = new ArrayList<Product>();
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostItem.
     */
    // TODO: Rename and change types and number of parameters
    public static PostItem newInstance(String param1, String param2) {
        PostItem fragment = new PostItem();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PostItem() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_post_item, container, false);
        Typeface mycustomFont =Typeface.createFromAsset(getActivity().getAssets(), "fonts/clanpro.ttf");
        spinner1= (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(view.getContext(), R.array.item_type, R.layout.spinner_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);
        spinner2= (Spinner) view.findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
        spinner3= (Spinner) view.findViewById(R.id.spinner3);
        spinner3.setOnItemSelectedListener(this);
        subCategory=(RelativeLayout)view.findViewById(R.id.subCategory);
        brandItem =(RelativeLayout) view.findViewById(R.id.itemBrand);
        searchLayout = (LinearLayout) view.findViewById(R.id.searchLayout);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        textView4.setTypeface(mycustomFont);
        searchView = (SearchView) view.findViewById(R.id.searchView3);
        listView = (ListView) view.findViewById(R.id.listView);
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
    public void onDestroy() {
        super.onDestroy();


    }
    @Override
    public void onStart()
    {
        super.onStart();
        ((MainActivity)getActivity()).hidePostIcon();
        ((MainActivity)getActivity()).setActionBarTitle("Post");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onPause(){
        super.onPause();
        ((MainActivity)getActivity()).showPostIcon();
        ((MainActivity)getActivity()).setActionBarTitle("");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(view!=null) myText=(TextView) view;
        c = myText.getText();
        String selected = ""+c;
        switch(selected){

            case "Electronics":
            {
                typename1=selected;
                itemtype = R.array.electronics;
                adapter2= ArrayAdapter.createFromResource(view.getContext(), itemtype, R.layout.spinner_item);
                spinner2.setAdapter(adapter2);

                subCategory.setVisibility(View.VISIBLE);
                brandItem.setVisibility(View.INVISIBLE);
                searchLayout.setVisibility(View.INVISIBLE);
                productResults.clear();
                break;
            }
            case "Vehicles":
            {
                typename1=selected;
                itemtype = R.array.vehicles;
                adapter2= ArrayAdapter.createFromResource(view.getContext(), itemtype, R.layout.spinner_item);
                spinner2.setAdapter(adapter2);

                subCategory.setVisibility(View.VISIBLE);
                brandItem.setVisibility(View.INVISIBLE);
                searchLayout.setVisibility(View.INVISIBLE);
                productResults.clear();
                break;
            }

            case "Desktop":
            {
                typename2=selected;
                itemtype2=R.array.desktop;
                adapter3= ArrayAdapter.createFromResource(view.getContext(), itemtype2,R.layout.spinner_item);
                spinner3.setAdapter(adapter3);

                brandItem.setVisibility(View.VISIBLE);
                searchLayout.setVisibility(View.INVISIBLE);
                productResults.clear();
                break;
            }
            case "Laptop":
            {
                typename2=selected;
                itemtype2=R.array.laptop;
                adapter3= ArrayAdapter.createFromResource(view.getContext(), itemtype2, R.layout.spinner_item);
                spinner3.setAdapter(adapter3);

                brandItem.setVisibility(View.VISIBLE);

                searchLayout.setVisibility(View.INVISIBLE);
                productResults.clear();
                break;
            }
            case "Monitor":
            {
                typename2=selected;
                itemtype2=R.array.monitor;
                adapter3= ArrayAdapter.createFromResource(view.getContext(), itemtype2, R.layout.spinner_item);
                spinner3.setAdapter(adapter3);

                brandItem.setVisibility(View.VISIBLE);

                searchLayout.setVisibility(View.INVISIBLE);
                productResults.clear();
                break;
            }
            case "Mouse":
            {
                typename2=selected;
                itemtype2=R.array.mouse;
                adapter3= ArrayAdapter.createFromResource(view.getContext(), itemtype2, R.layout.spinner_item);
                spinner3.setAdapter(adapter3);

                brandItem.setVisibility(View.VISIBLE);

                searchLayout.setVisibility(View.INVISIBLE);
                productResults.clear();
                break;
            }
            case "Headphone":
            {
                typename2=selected;
                itemtype2=R.array.headphone;
                adapter3= ArrayAdapter.createFromResource(view.getContext(), itemtype2, R.layout.spinner_item);
                spinner3.setAdapter(adapter3);

                brandItem.setVisibility(View.VISIBLE);

                searchLayout.setVisibility(View.INVISIBLE);
                productResults.clear();
                break;
            }
            case "TV":
            {
                typename2=selected;
                itemtype2=R.array.tv;
                adapter3= ArrayAdapter.createFromResource(view.getContext(), itemtype2, R.layout.spinner_item);
                spinner3.setAdapter(adapter3);

                brandItem.setVisibility(View.VISIBLE);

                searchLayout.setVisibility(View.INVISIBLE);
                productResults.clear();
                break;
            }
            case "Smart Phone":
            {
                typename2=selected;
                itemtype2=R.array.smartPhone;
                adapter3= ArrayAdapter.createFromResource(view.getContext(), itemtype2, R.layout.spinner_item);
                spinner3.setAdapter(adapter3);

                brandItem.setVisibility(View.VISIBLE);

                searchLayout.setVisibility(View.INVISIBLE);
                productResults.clear();
                break;
            }
            case "Phone Accessory":
            {
                typename2=selected;
                itemtype2=R.array.phoneAccessory;
                adapter3= ArrayAdapter.createFromResource(view.getContext(), itemtype2,R.layout.spinner_item);
                spinner3.setAdapter(adapter3);

                brandItem.setVisibility(View.VISIBLE);

                searchLayout.setVisibility(View.INVISIBLE);
                productResults.clear();
                break;
            }
            case "Samsung":
            {
                brand = selected;
                searchLayout.setVisibility(View.VISIBLE);

                searchLayout.setFocusableInTouchMode(true);
                searchLayout.setFocusable(true);
                searchLayout.requestFocus();
                searchView.setIconified(false);

                break;
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
   /* public void filterProductArray(String newText)
    {

        String pName;

        filteredProductResults.clear();
        for (int i = 0; i < productResults.size(); i++)
        {
            pName = productResults.get(i).getProductName().toLowerCase();
            if ( pName.contains(newText.toLowerCase()))
            {
                filteredProductResults.add(productResults.get(i));
            }
        }

    }*/
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

                    if (matchFound == "N"&&spinner1.getSelectedItem().toString().equals(tempProduct.getProductCategory())&&spinner2.getSelectedItem().toString().equals(tempProduct.getProductSubCategory()))
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

        private ArrayList<Product> productDetails=new ArrayList<Product>();
        int count;
        Typeface type;
        Context context;

        //constructor method
        public SimplifiedSearchResultsAdapter(Context context, ArrayList<Product> product_details) {

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

            TextView productName;
            final Product tempProduct = productDetails.get(position);


            convertView = layoutInflater.inflate(R.layout.listfour_simplifiedsearchresults, null);
            productName = (TextView) convertView.findViewById(R.id.product_name);
            RelativeLayout productList = (RelativeLayout) convertView.findViewById(R.id.productList);

            productName.setText(tempProduct.getProductName());

            productList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("product_name", tempProduct.getProductName());
                    bundle.putString("product_category",spinner1.getSelectedItem().toString());
                    bundle.putString("product_sub_category",spinner2.getSelectedItem().toString());
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
