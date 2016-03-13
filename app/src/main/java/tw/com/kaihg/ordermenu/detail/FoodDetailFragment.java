package tw.com.kaihg.ordermenu.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tw.com.kaihg.ordermenu.FoodModel;
import tw.com.kaihg.ordermenu.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FoodDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FoodDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodDetailFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private FoodModel mModel;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param model Parameter 1.
     * @return A new instance of fragment FoodDetailFragment.
     */
    public static FoodDetailFragment newInstance(FoodModel model) {
        FoodDetailFragment fragment = new FoodDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, model);
        fragment.setArguments(args);
        return fragment;
    }

    public FoodDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mModel = (FoodModel) getArguments().getSerializable(ARG_PARAM1);
        } else {
            if (savedInstanceState != null && savedInstanceState.getSerializable(ARG_PARAM1) != null) {
                mModel = (FoodModel) savedInstanceState.getSerializable(ARG_PARAM1);
            } else {
                mModel = new FoodModel();
                mModel.setFoodName("unknown item");
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) view.findViewById(R.id.foodDetail_image);
        Picasso.with(getContext()).load(mModel.getImageUrl()).placeholder(R.drawable.default_food).into(imageView);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mModel.getFoodName());
        ((TextView) view.findViewById(R.id.foodDetail_price)).setText(getString(R.string.food_price, mModel.getPrice()));

        view.findViewById(R.id.foodDetail_addToCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addToCart(mModel);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

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
        public void addToCart(FoodModel foodModel);
    }

}
