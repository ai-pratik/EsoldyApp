package com.enfield.esoldy;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link resetpassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class resetpassword extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public resetpassword() {
        // Required empty public constructor
    }

    private EditText registerdemail;
    private Button resetpasswordbtn;
    private TextView goback;

    private FrameLayout parentFrameLayout;
    private FirebaseAuth firebaseAuth;

    private ViewGroup Emailiconcontainer;
    private ImageView emailicon;
    private TextView emailicontext;

    private ProgressBar progressBar;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment resetpassword.
     */
    // TODO: Rename and change types and number of parameters
    public static resetpassword newInstance(String param1, String param2) {
        resetpassword fragment = new resetpassword();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_resetpassword, container, false);
        registerdemail = view.findViewById(R.id.forgotPasswordEmail);
        resetpasswordbtn = view.findViewById(R.id.ResetPasswordBtn);
        goback = view.findViewById(R.id.tv_forgotPasswordGoback);

        Emailiconcontainer=view.findViewById(R.id.forgotPasswordemailiconlinearLayout);
        emailicon=view.findViewById(R.id.forgotpasswordemailicon);
        emailicontext=view.findViewById(R.id.forgetpasswordsentext);
        progressBar=view.findViewById(R.id.forgotpassword_progressBar);


        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        firebaseAuth = FirebaseAuth.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerdemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        resetpasswordbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(Emailiconcontainer);
                emailicontext.setVisibility(View.GONE);
                TransitionManager.beginDelayedTransition(Emailiconcontainer);
                emailicon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                resetpasswordbtn.setEnabled(false);
                resetpasswordbtn.setTextColor(Color.argb(50, 255, 255, 255));


                firebaseAuth.sendPasswordResetEmail(registerdemail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //ScaleAnimation scaleAnimation=new ScaleAnimation(1,0,1,0);
                                    //Toast.makeText(getActivity(), "Email sent sucessfully", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    TransitionManager.beginDelayedTransition(Emailiconcontainer);
                                    emailicontext.setVisibility(View.VISIBLE);
                                } else {
                                    String error = task.getException().getMessage();
                                    //Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                    emailicontext.setText(error);
                                    emailicontext.setTextColor(getResources().getColor((R.color.btnred)));
                                    TransitionManager.beginDelayedTransition(Emailiconcontainer);
                                    emailicontext.setVisibility(View.VISIBLE);

                                }
                                resetpasswordbtn.setEnabled(true);
                                resetpasswordbtn.setTextColor(Color.rgb(255, 255, 255));
                            }
                        });
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });
    }

    private void checkinputs() {
        if (TextUtils.isEmpty(registerdemail.getText())) {
            resetpasswordbtn.setEnabled(false);
            resetpasswordbtn.setTextColor(Color.argb(50, 255, 255, 255));

        } else {
            resetpasswordbtn.setEnabled(true);
            resetpasswordbtn.setTextColor(Color.rgb(255, 255, 255));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}