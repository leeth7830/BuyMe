package kappa.buyme;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Taehyon on 10/6/2015.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{
    int ICONS[] = {R.drawable.ic_electronics,R.drawable.vehicle,R.drawable.ic_book,R.drawable.ic_order,R.drawable.ic_wish,R.drawable.ic_nearby,R.drawable.ic_setting,R.drawable.ic_contact,R.drawable.ic_legal,R.drawable.ic_help,R.drawable.ic_logout};
    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    public HomeFragment(){}
    private ExpandableListView mCategoryList;
    private ArrayList<Category> category_name = new ArrayList<Category>();
    private ArrayList<ArrayList<SubCategory>> subcategory_name = new ArrayList<ArrayList<SubCategory>>();
    private ArrayList<Integer> subCatCount = new ArrayList<Integer>();
    SearchView searchView;

    int previousGroup;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

       /* this.getCatData();
        mCategoryList = (ExpandableListView) rootView.findViewById(R.id.list);
        mCategoryList.setAdapter(new expandableListViewAdapter(rootView.getContext(), category_name, subcategory_name, subCatCount,ICONS));
        mCategoryList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view,
                                        int groupPosition, long id) {


                if (parent.isGroupExpanded(groupPosition))
                {
                    parent.collapseGroup(groupPosition);
                } else
                {
                    if (groupPosition != previousGroup)
                    {
                        parent.collapseGroup(previousGroup);
                    }
                    previousGroup = groupPosition;
                    parent.expandGroup(groupPosition);
                }

                parent.smoothScrollToPosition(groupPosition);
                return true;
            }

        });

        //defining the behavior when any child is clicked in expandable listview
        mCategoryList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                //calling CatWiseSearchResults with parameters of subcat code.
                //CatWiseSearchResults will fetch items based on subcatcode.

                Intent intent = new Intent(getView().getContext(), CatWiseSearchResults.class);

                ArrayList<SubCategory> tempList = new ArrayList<SubCategory>();
                tempList = subcategory_name.get(groupPosition);

                intent.putExtra("subcategory", tempList.get(childPosition).getSubCatCode());
                startActivity(intent);
                return true;
            }
        });*/
        return rootView;

    }
    @Override
    public void onStart(){
        super.onStart();
        ((MainActivity)getActivity()).setActionBarElevation(0);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
    @Override
    public void onPause(){
        super.onPause();
        ((MainActivity)getActivity()).setActionBarElevation((int)(5*getResources().getDisplayMetrics().density+0.5f));
    }

    public class expandableListViewAdapter extends BaseExpandableListAdapter
    {

        private LayoutInflater layoutInflater;
        private ArrayList<Category> categoryName=new ArrayList<Category>();
        ArrayList<ArrayList<SubCategory>> subCategoryName = new ArrayList<ArrayList<SubCategory>>();
        ArrayList<Integer> subCategoryCount = new ArrayList<Integer>();
        private int mIcons[];
        int count;
        Typeface type;

        SubCategory singleChild = new SubCategory();

        public expandableListViewAdapter(Context context, ArrayList<Category> categoryName, ArrayList<ArrayList<SubCategory>> subCategoryName, ArrayList<Integer> subCategoryCount,int Icons[])
        {

            layoutInflater = LayoutInflater.from(context);
            this.categoryName= categoryName;
            this.subCategoryName = subCategoryName;
            this.subCategoryCount = subCategoryCount;
            this.count= categoryName.size();
            this.mIcons = Icons;
        }

        @Override
        public void onGroupCollapsed(int groupPosition)
        {
            super.onGroupCollapsed(groupPosition);
        }

        @Override
        public void onGroupExpanded(int groupPosition)
        {
            super.onGroupExpanded(groupPosition);
        }

        @Override
        public int getGroupCount()
        {

            return categoryName.size();
        }

        @Override
        public int getChildrenCount(int i)
        {

            return (subCategoryCount.get(i));

        }

        @Override
        public Object getGroup(int i)
        {
            return categoryName.get(i).getCatName();
        }

        @Override
        public SubCategory getChild(int i, int i1)
        {

            ArrayList<SubCategory> tempList = new ArrayList<SubCategory>();
            tempList =  subCategoryName.get(i);
            return tempList.get(i1);

        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup)
        {

            if (view == null)
            {
                view = layoutInflater.inflate(R.layout.expandablelistcategory, viewGroup, false);
            }
            ImageView imageView = (ImageView) view.findViewById(R.id.rowIcon);
            imageView.setImageResource(mIcons[i]);

            TextView textView = (TextView) view.findViewById(R.id.cat_desc_1);
            textView.setText(getGroup(i).toString());
            textView.setTypeface(type);

            return view;

        }


        @Override
        public View getChildView(int i, int i1, boolean isExpanded, View view, ViewGroup viewGroup)
        {
            if (view == null)
            {
                view = layoutInflater.inflate(R.layout.expandablelistviewsubcat, viewGroup, false);

            }

            singleChild = getChild(i,i1);

            TextView childSubCategoryName = (TextView) view.findViewById(R.id.subcat_name);
            childSubCategoryName.setTypeface(type);

            childSubCategoryName.setText(singleChild.getSubCatName());

            return view;

        }

        @Override
        public boolean isChildSelectable(int i, int i1)
        {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }

    }
    public void getCatData()
    {
        category_name.clear();
        Category categoryDetails = new Category();

        categoryDetails.setCatCode(10);
        categoryDetails.setCatName("Electronics");

        category_name.add(categoryDetails);

        categoryDetails = new Category();
        categoryDetails.setCatCode(20);
        categoryDetails.setCatName("Vehicles");
        category_name.add(categoryDetails);

        categoryDetails = new Category();
        categoryDetails.setCatCode(30);
        categoryDetails.setCatName("Books");
        category_name.add(categoryDetails);

        categoryDetails = new Category();
        categoryDetails.setCatCode(40);
        categoryDetails.setCatName("Home");
        category_name.add(categoryDetails);

        categoryDetails = new Category();
        categoryDetails.setCatCode(50);
        categoryDetails.setCatName("Personal Care");
        category_name.add(categoryDetails);

        categoryDetails = new Category();
        categoryDetails.setCatCode(60);
        categoryDetails.setCatName("Clothes");
        category_name.add(categoryDetails);

        categoryDetails = new Category();
        categoryDetails.setCatCode(70);
        categoryDetails.setCatName("Outdoor");
        category_name.add(categoryDetails);

        categoryDetails = new Category();
        categoryDetails.setCatCode(80);
        categoryDetails.setCatName("Materials");
        category_name.add(categoryDetails);

        //----Populate Sub Category Codes
        subcategory_name.clear();

        ArrayList<SubCategory> subCategoryMatches = new ArrayList<SubCategory>();

        SubCategory subCategoryMatch = new SubCategory();

        subCategoryMatch.setSubCatName("Computers");
        subCategoryMatch.setSubCatCode("1001");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Laptop");
        subCategoryMatch.setSubCatCode("1002");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Monitor");
        subCategoryMatch.setSubCatCode("1003");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Mouse");
        subCategoryMatch.setSubCatCode("1004");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Headphone");
        subCategoryMatch.setSubCatCode("1005");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("TV");
        subCategoryMatch.setSubCatCode("1006");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Smart Phone");
        subCategoryMatch.setSubCatCode("1007");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Phone Accessory");
        subCategoryMatch.setSubCatCode("1008");
        subCategoryMatches.add(subCategoryMatch);

        subcategory_name.add(subCategoryMatches);
        subCatCount.add(subCategoryMatches.size());
        //---

        subCategoryMatches = new ArrayList<SubCategory>();

        subCategoryMatch = new SubCategory();

        subCategoryMatch.setSubCatName("Small Car");
        subCategoryMatch.setSubCatCode("2001");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Midsize Car");
        subCategoryMatch.setSubCatCode("2002");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Large Car");
        subCategoryMatch.setSubCatCode("2003");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Luxury Car");
        subCategoryMatch.setSubCatCode("2004");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Sports Car");
        subCategoryMatch.setSubCatCode("2005");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Motorcycle");
        subCategoryMatch.setSubCatCode("2006");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Truck");
        subCategoryMatch.setSubCatCode("2007");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Bicycle");
        subCategoryMatch.setSubCatCode("2008");
        subCategoryMatches.add(subCategoryMatch);

        subcategory_name.add(subCategoryMatches);
        subCatCount.add(subCategoryMatches.size());

        //---

        subCategoryMatches = new ArrayList<SubCategory>();

        subCategoryMatch = new SubCategory();

        subCategoryMatch.setSubCatName("Novel");
        subCategoryMatch.setSubCatCode("3001");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Textbook");
        subCategoryMatch.setSubCatCode("3002");
        subCategoryMatches.add(subCategoryMatch);


        subcategory_name.add(subCategoryMatches);
        subCatCount.add(subCategoryMatches.size());

        //---

        subCategoryMatches = new ArrayList<SubCategory>();

        subCategoryMatch = new SubCategory();

        subCategoryMatch.setSubCatName("Furniture");
        subCategoryMatch.setSubCatCode("4001");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Kitchen");
        subCategoryMatch.setSubCatCode("4002");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Dining");
        subCategoryMatch.setSubCatCode("4003");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Bedding");
        subCategoryMatch.setSubCatCode("4004");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Bath");
        subCategoryMatch.setSubCatCode("4005");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Craft and Decoration");
        subCategoryMatch.setSubCatCode("4006");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Pet Supply");
        subCategoryMatch.setSubCatCode("4007");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Baby Supply");
        subCategoryMatch.setSubCatCode("4008");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Garden");
        subCategoryMatch.setSubCatCode("4009");
        subCategoryMatches.add(subCategoryMatch);

        subcategory_name.add(subCategoryMatches);
        subCatCount.add(subCategoryMatches.size());

        //---

        subCategoryMatches = new ArrayList<SubCategory>();

        subCategoryMatch = new SubCategory();

        subCategoryMatch.setSubCatName("Soaps & Handwash");
        subCategoryMatch.setSubCatCode("5001");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Body Care");
        subCategoryMatch.setSubCatCode("5002");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Hair Care");
        subCategoryMatch.setSubCatCode("5003");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Mens Grooming");
        subCategoryMatch.setSubCatCode("5004");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Oral Care");
        subCategoryMatch.setSubCatCode("5005");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Cosmetics");
        subCategoryMatch.setSubCatCode("5006");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Facial Care");
        subCategoryMatch.setSubCatCode("5007");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Feminine Hygiene");
        subCategoryMatch.setSubCatCode("5008");
        subCategoryMatches.add(subCategoryMatch);

        subcategory_name.add(subCategoryMatches);
        subCatCount.add(subCategoryMatches.size());

        //---baby and kids

        subCategoryMatches = new ArrayList<SubCategory>();

        subCategoryMatch = new SubCategory();

        subCategoryMatch.setSubCatName("Baby Food");
        subCategoryMatch.setSubCatCode("6001");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Baby Care");
        subCategoryMatch.setSubCatCode("6002");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Diapers");
        subCategoryMatch.setSubCatCode("6003");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Kids");
        subCategoryMatch.setSubCatCode("6004");
        subCategoryMatches.add(subCategoryMatch);

        subcategory_name.add(subCategoryMatches);
        subCatCount.add(subCategoryMatches.size());

        //--- cleaning

        subCategoryMatches = new ArrayList<SubCategory>();

        subCategoryMatch = new SubCategory();

        subCategoryMatch.setSubCatName("Fabric");
        subCategoryMatch.setSubCatCode("7001");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Utensil");
        subCategoryMatch.setSubCatCode("7002");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Household");
        subCategoryMatch.setSubCatCode("7003");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Toilet");
        subCategoryMatch.setSubCatCode("7004");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Shoes");
        subCategoryMatch.setSubCatCode("7005");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Tissue Rolls");
        subCategoryMatch.setSubCatCode("7006");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Brooms & Brushes");
        subCategoryMatch.setSubCatCode("7007");
        subCategoryMatches.add(subCategoryMatch);

        subcategory_name.add(subCategoryMatches);
        subCatCount.add(subCategoryMatches.size());

        //---household goods

        subCategoryMatches = new ArrayList<SubCategory>();

        subCategoryMatch = new SubCategory();

        subCategoryMatch.setSubCatName("Freshners");
        subCategoryMatch.setSubCatCode("8001");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Repellents");
        subCategoryMatch.setSubCatCode("8002");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Pooja Needs");
        subCategoryMatch.setSubCatCode("8003");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Bulbs and CFLs");
        subCategoryMatch.setSubCatCode("8004");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("OTC Medicines");
        subCategoryMatch.setSubCatCode("8005");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Batteries");
        subCategoryMatch.setSubCatCode("8006");
        subCategoryMatches.add(subCategoryMatch);

        subCategoryMatch = new SubCategory();
        subCategoryMatch.setSubCatName("Disposibles & Napkins");
        subCategoryMatch.setSubCatCode("8007");
        subCategoryMatches.add(subCategoryMatch);

        subcategory_name.add(subCategoryMatches);
        subCatCount.add(subCategoryMatches.size());

    }
}
