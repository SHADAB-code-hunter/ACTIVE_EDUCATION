package com.gt.active_education;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by GT on 12/17/2017.
 */

public class RecyclerView_Selected_Test extends AppCompatActivity implements SelectableViewHolder.OnItemSelectedListener {

    RecyclerView recyclerView;
    SelectableAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler_selected);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) this.findViewById(R.id.selection_list);
        recyclerView.setLayoutManager(layoutManager);
        List<Item> selectableItems = generateItems();
        adapter = new SelectableAdapter(this,selectableItems,true);
        recyclerView.setAdapter(adapter);
    }
/*
    private void loadFirstPage() {
        Log.d("firstload", "loadFirstPage: ");
        progressDialog = new ProgressDialog(RecyclerView_Selected_Test.this);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.Loading));
        new NORMAL_ASYNCHTASK(new NORMAL_ASYNCHTASK.JOBJ_LISTENER() {
            @Override
            public void on_listener(JSONObject jsonobject, String loginApi) {
                try {

                 for(int i-0; i<)
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },map.toString(),TOP_INNER_SCHOOL_FILTER_DEFAULT+1,"data").execute();

        Log.d("psged",""+currentPage);
        if (currentPage <= TOTAL_PAGES){}// school_adapter_test.addLoadingFooter();
        else isLastPage = true;

    }
*/

    public List<Item> generateItems(){

        List<Item> selectableItems = new ArrayList<>();
        selectableItems.add(new Item("cem","karaca"));
        selectableItems.add(new Item("sezen","aksu"));
        selectableItems.add(new Item("baris","manco"));

        selectableItems.add(new Item("wee","wer"));
        selectableItems.add(new Item("octc","ddddd"));
        selectableItems.add(new Item("rfr","gtg"));

        selectableItems.add(new Item("22e","y6y"));
        selectableItems.add(new Item("qqq","r4r4"));
        selectableItems.add(new Item("t5t","6y6y"));

        selectableItems.add(new Item("ikik","lol"));
        selectableItems.add(new Item("6y6","i8i8"));
        selectableItems.add(new Item("55t","y6y"));


        selectableItems.add(new Item("aws","ded"));
        selectableItems.add(new Item("frf","gthy"));
        selectableItems.add(new Item("vh","rdddddde"));

        selectableItems.add(new Item("frrf","gtgtg"));
        selectableItems.add(new Item("yhyjuj","jukik"));
        selectableItems.add(new Item("3ews","wdsc"));

        selectableItems.add(new Item("frdscsd","6778j"));
        selectableItems.add(new Item("vgtb","ololo,"));
        selectableItems.add(new Item("swxwd","ujj"));

        selectableItems.add(new Item("lolo.","hyy"));
        selectableItems.add(new Item("rfrfr","yjuuj"));
        selectableItems.add(new Item("sxsxsx","gtgt"));

/**/
        selectableItems.add(new Item("cem","karaca"));
        selectableItems.add(new Item("sezen","aksu"));
        selectableItems.add(new Item("baris","manco"));

        selectableItems.add(new Item("wee","wer"));
        selectableItems.add(new Item("octc","ddddd"));
        selectableItems.add(new Item("rfr","gtg"));

        selectableItems.add(new Item("22e","y6y"));
        selectableItems.add(new Item("qqq","r4r4"));
        selectableItems.add(new Item("t5t","6y6y"));

        selectableItems.add(new Item("ikik","lol"));
        selectableItems.add(new Item("6y6","i8i8"));
        selectableItems.add(new Item("55t","y6y"));


        selectableItems.add(new Item("aws","ded"));
        selectableItems.add(new Item("frf","gthy"));
        selectableItems.add(new Item("vh","rdddddde"));

        selectableItems.add(new Item("frrf","gtgtg"));
        selectableItems.add(new Item("yhyjuj","jukik"));
        selectableItems.add(new Item("3ews","wdsc"));

        selectableItems.add(new Item("frdscsd","6778j"));
        selectableItems.add(new Item("vgtb","ololo,"));
        selectableItems.add(new Item("swxwd","ujj"));

        selectableItems.add(new Item("lolo.","hyy"));
        selectableItems.add(new Item("rfrfr","yjuuj"));
        selectableItems.add(new Item("sxsxsx","gtgt"));
/**/
        selectableItems.add(new Item("cem","karaca"));
        selectableItems.add(new Item("sezen","aksu"));
        selectableItems.add(new Item("baris","manco"));

        selectableItems.add(new Item("wee","wer"));
        selectableItems.add(new Item("octc","ddddd"));
        selectableItems.add(new Item("rfr","gtg"));

        selectableItems.add(new Item("22e","y6y"));
        selectableItems.add(new Item("qqq","r4r4"));
        selectableItems.add(new Item("t5t","6y6y"));

        selectableItems.add(new Item("ikik","lol"));
        selectableItems.add(new Item("6y6","i8i8"));
        selectableItems.add(new Item("55t","y6y"));


        selectableItems.add(new Item("aws","ded"));
        selectableItems.add(new Item("frf","gthy"));
        selectableItems.add(new Item("vh","rdddddde"));

        selectableItems.add(new Item("frrf","gtgtg"));
        selectableItems.add(new Item("yhyjuj","jukik"));
        selectableItems.add(new Item("3ews","wdsc"));

        selectableItems.add(new Item("frdscsd","6778j"));
        selectableItems.add(new Item("vgtb","ololo,"));
        selectableItems.add(new Item("swxwd","ujj"));

        selectableItems.add(new Item("lolo.","hyy"));
        selectableItems.add(new Item("rfrfr","yjuuj"));
        selectableItems.add(new Item("sxsxsx","gtgt"));

/**/
        selectableItems.add(new Item("cem","karaca"));
        selectableItems.add(new Item("sezen","aksu"));
        selectableItems.add(new Item("baris","manco"));

        selectableItems.add(new Item("wee","wer"));
        selectableItems.add(new Item("octc","ddddd"));
        selectableItems.add(new Item("rfr","gtg"));

        selectableItems.add(new Item("22e","y6y"));
        selectableItems.add(new Item("qqq","r4r4"));
        selectableItems.add(new Item("t5t","6y6y"));

        selectableItems.add(new Item("ikik","lol"));
        selectableItems.add(new Item("6y6","i8i8"));
        selectableItems.add(new Item("55t","y6y"));


        selectableItems.add(new Item("aws","ded"));
        selectableItems.add(new Item("frf","gthy"));
        selectableItems.add(new Item("vh","rdddddde"));

        selectableItems.add(new Item("frrf","gtgtg"));
        selectableItems.add(new Item("yhyjuj","jukik"));
        selectableItems.add(new Item("3ews","wdsc"));

        selectableItems.add(new Item("frdscsd","6778j"));
        selectableItems.add(new Item("vgtb","ololo,"));
        selectableItems.add(new Item("swxwd","ujj"));

        selectableItems.add(new Item("lolo.","hyy"));
        selectableItems.add(new Item("rfrfr","yjuuj"));
        selectableItems.add(new Item("sxsxsx","gtgt"));

        return selectableItems;
    }

    @Override
    public void onItemSelected(SelectableItem selectableItem) {

        List<Item> selectedItems = adapter.getSelectedItems();
        Snackbar.make(recyclerView,"Selected item is "+selectableItem.getName()+
                ", Totally  selectem item count is "+selectedItems.size(),Snackbar.LENGTH_LONG).show();
    }
}