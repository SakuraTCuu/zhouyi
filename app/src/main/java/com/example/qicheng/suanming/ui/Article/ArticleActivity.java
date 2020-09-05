package com.example.qicheng.suanming.ui.Article;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.adapter.ArticleAdapter;
import com.example.qicheng.suanming.bean.ArticleBean;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.bean.VIPListDataBean;
import com.example.qicheng.suanming.contract.ArticleListContract;
import com.example.qicheng.suanming.presenter.ArticleListPresenter;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ArticleActivity extends BaseActivity implements ArticleListContract.View, AdapterView.OnItemClickListener {

    @BindView(R.id.lv_article)
    ListView lv_article;

    @BindView(R.id.recycle_article_list)
    RecyclerView recycle_article_list;

    private ArticleListPresenter mPresenter;
    private ArticleAdapter adapter;
    private List<ArticleBean.DataBean.ListBean> result;
    private List<VIPListDataBean.DataBean> toplist;
    private String articleId = "0";
    private String title = "";

    private ArticleListAdapter recycleAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_article;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");
        articleId = getIntent().getStringExtra("clickId");
        toplist = (List<VIPListDataBean.DataBean>) getIntent().getSerializableExtra("toplist");

        mPresenter = new ArticleListPresenter(this);

        initRecycleView();
    }

    @Override
    protected void initData() {
        setTitleText(title);
        Map<String, String> map = new HashMap<>();
        map.put("category_id", articleId);
        mPresenter.getArticleList(map);
        showLoading();
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void getArticleListSuc(String data) {
        hideLoading();
        ArticleBean bean = new Gson().fromJson(data, ArticleBean.class);
        result = bean.getData().getList();

        adapter = new ArticleAdapter(mContext, result);

        lv_article.setAdapter(adapter);
        lv_article.setOnItemClickListener(this);
    }

    private void initRecycleView() {
        //谷歌提供了一个默认的item删除添加的动画
        recycle_article_list.setItemAnimator(new DefaultItemAnimator());
        //谷歌提供了一个DividerItemDecoration的实现类来实现分割线
        //往往我们需要自定义分割线的效果，需要自己实现ItemDecoration接口
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
//        recycle_shop_list.addItemDecoration(dividerItemDecoration);

        recycle_article_list.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recycle_article_list.setLayoutManager(linearLayoutManager);
        recycleAdapter = new ArticleListAdapter(toplist);
        recycle_article_list.setAdapter(recycleAdapter);
    }

    public void updateIndex() {
        initData();
    }

    @Override
    public void setPresenter(ArticleListContract.Presenter presenter) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ArticleActivity.this, ArticleDetailActivity.class);
        intent.putExtra("id", result.get(position).getId() + "");
        intent.putExtra("name", result.get(position).getName() + "");

        startActivity(intent);
    }


    class ArticleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<VIPListDataBean.DataBean> list;

        public ArticleListAdapter(List<VIPListDataBean.DataBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = null;
            v = LayoutInflater.from(mContext).inflate(R.layout.layout_article_top_item, parent, false);
            RecyclerView.ViewHolder holder = null;
            holder = new MyViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolder) holder).tv_top_title.setText(list.get(position).getName());
            if (Integer.parseInt(articleId) == list.get(position).getId()) {
                ((MyViewHolder) holder).iv_top_index.setVisibility(View.VISIBLE);
            } else {
                ((MyViewHolder) holder).iv_top_index.setVisibility(View.INVISIBLE);
            }

            ((MyViewHolder) holder).ll_article_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    articleId = list.get(position).getId() + "";
                    title = list.get(position).getName();
                    recycleAdapter.notifyDataSetChanged();
                    //更新内容展示
                    updateIndex();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_top_title;
        public ImageView iv_top_index;
        public LinearLayout ll_article_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_top_title = (TextView) itemView.findViewById(R.id.tv_top_title);
            iv_top_index = (ImageView) itemView.findViewById(R.id.iv_top_index);
            ll_article_item = (LinearLayout) itemView.findViewById(R.id.ll_article_item);
        }
    }
}
