package kappa.buyme;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchKeyword.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchKeyword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchKeyword extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SearchView search;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchKeyword.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchKeyword newInstance(String param1, String param2) {
        SearchKeyword fragment = new SearchKeyword();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchKeyword() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_keyword, container, false);
        search = (SearchView) rootView.findViewById(R.id.searchView2);
        search.setQueryHint("Search for (TV, PC, etc)");
        search.setIconifiedByDefault(false);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("new_variable_name", query);
                    android.support.v4.app.Fragment searchFrag = new Search();
                    searchFrag.setArguments(bundle);
                    openFragment(searchFrag);

                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("new_variable_name", "Everything");
                    android.support.v4.app.Fragment searchFrag = new Search();
                    searchFrag.setArguments(bundle);
                    openFragment(searchFrag);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });



        return rootView;

}

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
           // getActivity().getSupportFragmentManager().beginTransaction().remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).commit();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("my_fragment").commit();
        }

    }

}
