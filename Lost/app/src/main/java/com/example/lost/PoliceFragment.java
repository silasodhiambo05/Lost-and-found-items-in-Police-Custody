package com.example.lost;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lost.PostViewActivity;
import com.example.lost.PostAdapter;
import com.example.lost.Post;
import com.example.lost.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class PoliceFragment extends Fragment{

    View view;

    private DatabaseReference databaseReference;

    private ListView listView;

    private List<Post> postList;

    public static final String POST_USER = "com.example.lost.postuser",
            POST_TITLE = "com.example.lost.posttitle",
            POST_DESCRIPTION = "com.example.lost.postdescription",
            POST_PHONE_NUMBER = "com.example.lost.postphonenumber",
            POST_ID = "com.example.lost.postid",
            POST_USER_ID = "com.example.lost.postuserid",
            POST_USER_EMAIL = "com.example.lost.postuseremail",
            POST_ROUTE = "com.example.lost.postpage";

    public PoliceFragment(){

    }

    public void refreshList(String search){
        FragmentActivity parentActivity = (FragmentActivity) view.getContext();

        List<Post> mFinalList = new ArrayList<>();

        for (Post p : postList) {
            if (p.getTitle().toLowerCase().contains(search.toLowerCase())) {
                mFinalList.add(p);
            }
        }

        PostAdapter postAdapter = new PostAdapter(parentActivity,mFinalList);
        //PostAdapter postAdapter = new PostAdapter(parentActivity,postList);
        //postAdapter.getFilter().filter(search);
        listView.setAdapter(postAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        view = inflater.inflate(R.layout.fragment_police,container,false);

        listView = (ListView) view.findViewById(R.id.listView);

        postList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("/FOUND");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.child("INFO").getValue(Post.class);
                    postList.add(post);
                }
                Collections.reverse(postList);
                FragmentActivity parentActivity = (FragmentActivity) view.getContext();
                PostAdapter postAdapter = new PostAdapter(parentActivity,postList);
                listView.setAdapter(postAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Post post = postList.get(i);
                Intent intent = new Intent(getActivity().getApplicationContext(), PostViewActivity.class);
                intent.putExtra(POST_USER, post.getUser());
                intent.putExtra(POST_TITLE, post.getTitle());
                intent.putExtra(POST_DESCRIPTION, post.getDescription());
                intent.putExtra(POST_PHONE_NUMBER, post.getPhoneNum());
                intent.putExtra(POST_ID, post.getPostId());
                intent.putExtra(POST_USER_ID, post.getUserId());
                intent.putExtra(POST_USER_EMAIL, post.getEmail());
                intent.putExtra(POST_ROUTE, "FOUND");
                startActivity(intent);
            }
        });

        return view;
    }
}
