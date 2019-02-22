package edu.iastate.graysonc.fastfood;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInAccount account;

    private Button signOutButton;
    private View signInButton;
    private ImageView avatarImageView;
    private TextView nameTextView;

    private boolean toggled;
    private TextView mUserInfoDisp;
    private TextView mUserDietaryDisp;
    private Button mMenuTicket;
    private Button mMenuEdit;
    private Button forceSignOn;
    private ImageButton mMenuExpand;
    private RequestQueue r;
    private ConstraintLayout user_singed_in;
    private EditText UIDIn;

    /**
     * Required Constructor
     */
    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        signInButton = getView().findViewById(R.id.sign_in_button);
        signOutButton = getView().findViewById(R.id.sign_out_button);
        avatarImageView = getView().findViewById(R.id.avatar_image_view);
        nameTextView = getView().findViewById(R.id.name_text_view);

        signInButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);

        //Initialize Variables and point to correct XML objects
        toggled = false;
        mUserInfoDisp = (TextView) getView().findViewById(R.id.user_info_display);
        mUserDietaryDisp = (TextView) getView().findViewById(R.id.user_dietary_display);
        r = Volley.newRequestQueue(getContext());
        mMenuTicket =(Button) getView().findViewById(R.id.TicketButton);
        mMenuEdit = (Button) getView().findViewById(R.id.ButtonEdit);
        mMenuExpand = (ImageButton) getView().findViewById(R.id.MenuButton);
        user_singed_in = (ConstraintLayout) getView().findViewById(R.id.user_signed_in);
        forceSignOn= getView().findViewById(R.id.ForceLogin);
        UIDIn = getView().findViewById(R.id.UID);


        forceSignOn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                user_singed_in.setVisibility(View.VISIBLE);
                signInButton.setVisibility(View.INVISIBLE);
                fetchUserData(UIDIn.getText().toString());
                UIDIn.setVisibility(View.INVISIBLE);
                forceSignOn.setVisibility(View.INVISIBLE);

            }
        });
        //Create Click Listeners
        mMenuEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createWarning("Open Edit Users Page");
            }
        });
        mMenuTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWarning("Open Submit Ticket");
            }
        });
        mMenuExpand.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                toggleMenuVisible();
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        account = GoogleSignIn.getLastSignedInAccount(getContext());
        updateUI(account);
    }

    /**
     * Toggles between signed in and signed out Guis
     * @param _account account being handled
     */
    public void updateUI(GoogleSignInAccount _account) {
        if (_account == null) { // User is not signed in
            signOutButton.setVisibility(View.INVISIBLE);
            signInButton.setVisibility(View.VISIBLE);
            user_singed_in.setVisibility(View.INVISIBLE);
            avatarImageView.setImageBitmap(null);
            nameTextView.setText("Not signed in");
        } else { // User is signed in
            //TODO fetchUserData(_account.getEmail());
            signInButton.setVisibility(View.INVISIBLE);
            user_singed_in.setVisibility(View.VISIBLE);
            nameTextView.setText(_account.getDisplayName());
            fetchUserData(account.getEmail());
            Uri avatarUri = _account.getPhotoUrl();
            if (avatarUri != null) {
                DownloadImageTask imageDownloader = new DownloadImageTask(avatarImageView); // Downloads the user's avatar asynchronously
                imageDownloader.execute(avatarUri.toString());
            }
        }
    }

    /**
     * Uses Google Api To Sign In
     */
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Uses Google Api To Sign Out
     */
    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                        toggleMenuVisible();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /**
     * Handles Sign in, passes account etc
     * @param completedTask
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(account);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
        }
    }
    /**
     * Toggles Visibility Of Buttons
     */
    public void toggleMenuVisible(){
        if(toggled){
            mMenuTicket.setVisibility(View.INVISIBLE);
            mMenuEdit.setVisibility(View.INVISIBLE);
            signOutButton.setVisibility(View.INVISIBLE);
        }else{
            mMenuTicket.setVisibility(View.VISIBLE);
            mMenuEdit.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.VISIBLE);
        }
        toggled=!toggled;
    }

    /**
     * Creates A Toast Message With Content @message
     * @param message The message to be displayed
     */
    public void createWarning(String message){
        Context context = getContext();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Fetches user data
     * @param UID The Unique Id to poll for
     */
    private void fetchUserData(final String UID){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://cs309-bs-1.misc.iastate.edu:8080/users/" + UID  , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    mUserInfoDisp.setText("");
                    mUserDietaryDisp.setText("");
                    mUserInfoDisp.append(""+response.toString());
                } catch (Exception e) {
                    mUserDietaryDisp.append(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.toString().startsWith("com.android.volley.ParseError:")){
                    createUser(UID);
                    try {
                        TimeUnit.SECONDS.sleep(1); //TODO Not this
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    fetchUserData(UID);
                }
                mUserDietaryDisp.append(error.toString() + "\n");
            }
        });

        r.add(request); //Actually processes request
    }

    /**
     * Creates a request to a page and formats it, appending results to the main screen
     * @param URL The Page To Process
     */
    private void jsonParse(String URL) {
        //Creates a new Json Request with volley, handles it and appends it.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mUserInfoDisp.setText("");
                    mUserDietaryDisp.setText("");
                    JSONArray jsonArray = response.getJSONArray("users"); //Retrieves data from employees section of json
                    int i = new Random(System.currentTimeMillis()).nextInt(jsonArray.length());
                    JSONObject user = jsonArray.getJSONObject(i); //Each individual user in the json file
                    //Pulling data from json to variables
                    String firstName = user.getString("firstname"); //pull data out to a string
                    int age = user.getInt("age");
                    mUserInfoDisp.append("Age: " + age +".\n");
                    String mail = user.getString("mail");
                    mUserInfoDisp.append("Email: " +mail + ", \n");
                    String dietary = user.getString("dietary");
                    mUserDietaryDisp.append(dietary + "\n\n\n\n\n");
                    String fact = user.getString("fact");
                    mUserInfoDisp.append("My Fun Fact:" +fact + "\n");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        r.add(request); //Actually processes request
    }

    /**
     * Creates a user and adds them to the database
     * @param UID Email address to be used as a uuid
     */
    private void createUser(String UID) {
        JSONObject js = new JSONObject();
        try {
            js.put("email", UID);
            js.put("userType", "registered");
        } catch (Exception e) {
            createWarning(e.getMessage());
        }
        createWarning(js.toString());
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, "http://cs309-bs-1.misc.iastate.edu:8080/users/create",js,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        mUserDietaryDisp.setText(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mUserDietaryDisp.setText(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        r.add(postRequest);
    }
}
