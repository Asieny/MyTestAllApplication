package com.ithei.andy.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import videoView.otherDemo.BaseActivity;
import videoView.otherDemo.ToastUtils;

/**
 * @创建者 AndyYan
 * @创建时间 2018/12/25 16:08
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class MyRetrofitActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_test;
    private String headUrl = "http://118.89.109.106:8080/YKTJK/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {//RxJava响应式编程
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_use_retrofit);
        initView();
        initData();
    }

    private void initView() {
        bt_test = (Button) findViewById(R.id.bt_test);
    }

    private void initData() {
        bt_test.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_test:
                requestIntnet();
                break;
        }
    }

    private void requestIntnet() {
        Retrofit retrofit = new Retrofit.Builder()
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(headUrl)
                .build();
        Call<News> news  = retrofit.create(APi.class).getNews(24);
        news.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                ToastUtils.showToast(""+response.body().arr.id);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

    public interface APi {
        @GET("serDetailInfo")
        Call<News> getNews(@Query("userid") Integer userid);
    }

    //使用场景：@Header用于添加不固定的请求头，@Headers用于添加固定的请求头
    //使用范围：@Header作用于方法的参数；@Headers作用于方法

    /*//@Header
    @GET("user")
    Call<User> getUser(@Header("Authorization") String authorization)

    //@Headers
    @Headers("Authorization:authorization")
    @GET("user")
    Call<User> getUser()*/

    public static class News {

        /**
         * arr : {"fstatus":"2","listProject":[{"faddress":"杭州湾大道110号","fname":"杭州湾小区","xinxi":"000000004D928CFBCEAA6C01A48911B2","authority_group_id":"8a80808665c7c0520165eb3e8e0c0058","project_name":"杭州湾小区","cardno":"AE4E714800000000","relation":"屋主","lanyaid":"","user_id":24,"project_id":"8a80808665c7c0520165eb3189440045","projectdetail_id":"8a80808665c7c0520165eb37db5d004f","miyao":"F0F1F2F3F4F5F6F7F8F9FAFBFCFDFEFF","id":37,"id_pic":"1537249941275.jpeg","lanya":"","status":"2"},{"faddress":"dcvv","fname":"深圳南山小区","xinxi":"000000004D928CFBCEAA6C01A48911B2","authority_group_id":"8a80808665ef36300165ef5d5a96002c","project_name":"深圳南山小区","cardno":"AE4E714800000000","relation":"dghb","lanyaid":"8a80808665ef36300165efa6c5f50038,8a80808665ef36300165efa713d7003a","user_id":24,"project_id":"8a80808665c7c0520165eb35c1760047","projectdetail_id":"8a80808665ef36300165ef5ba8620022","miyao":"F0F1F2F3F4F5F6F7F8F9FAFBFCFDFEFF","id":39,"id_pic":"1538009452657.jpeg","lanya":"停车库,前门","status":"2"}],"user_name":"瓜","id":24}
         */

        private ArrBean arr;

        public ArrBean getArr() {
            return arr;
        }

        public void setArr(ArrBean arr) {
            this.arr = arr;
        }

        public static class ArrBean {
            /**
             * fstatus : 2
             * listProject : [{"faddress":"杭州湾大道110号","fname":"杭州湾小区","xinxi":"000000004D928CFBCEAA6C01A48911B2","authority_group_id":"8a80808665c7c0520165eb3e8e0c0058","project_name":"杭州湾小区","cardno":"AE4E714800000000","relation":"屋主","lanyaid":"","user_id":24,"project_id":"8a80808665c7c0520165eb3189440045","projectdetail_id":"8a80808665c7c0520165eb37db5d004f","miyao":"F0F1F2F3F4F5F6F7F8F9FAFBFCFDFEFF","id":37,"id_pic":"1537249941275.jpeg","lanya":"","status":"2"},{"faddress":"dcvv","fname":"深圳南山小区","xinxi":"000000004D928CFBCEAA6C01A48911B2","authority_group_id":"8a80808665ef36300165ef5d5a96002c","project_name":"深圳南山小区","cardno":"AE4E714800000000","relation":"dghb","lanyaid":"8a80808665ef36300165efa6c5f50038,8a80808665ef36300165efa713d7003a","user_id":24,"project_id":"8a80808665c7c0520165eb35c1760047","projectdetail_id":"8a80808665ef36300165ef5ba8620022","miyao":"F0F1F2F3F4F5F6F7F8F9FAFBFCFDFEFF","id":39,"id_pic":"1538009452657.jpeg","lanya":"停车库,前门","status":"2"}]
             * user_name : 瓜
             * id : 24
             */

            private String                fstatus;
            private String                user_name;
            private int                   id;
            private List<ListProjectBean> listProject;

            public String getFstatus() {
                return fstatus;
            }

            public void setFstatus(String fstatus) {
                this.fstatus = fstatus;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public List<ListProjectBean> getListProject() {
                return listProject;
            }

            public void setListProject(List<ListProjectBean> listProject) {
                this.listProject = listProject;
            }

            public static class ListProjectBean {
                /**
                 * faddress : 杭州湾大道110号
                 * fname : 杭州湾小区
                 * xinxi : 000000004D928CFBCEAA6C01A48911B2
                 * authority_group_id : 8a80808665c7c0520165eb3e8e0c0058
                 * project_name : 杭州湾小区
                 * cardno : AE4E714800000000
                 * relation : 屋主
                 * lanyaid :
                 * user_id : 24
                 * project_id : 8a80808665c7c0520165eb3189440045
                 * projectdetail_id : 8a80808665c7c0520165eb37db5d004f
                 * miyao : F0F1F2F3F4F5F6F7F8F9FAFBFCFDFEFF
                 * id : 37
                 * id_pic : 1537249941275.jpeg
                 * lanya :
                 * status : 2
                 */

                private String faddress;
                private String fname;
                private String xinxi;
                private String authority_group_id;
                private String project_name;
                private String cardno;
                private String relation;
                private String lanyaid;
                private int    user_id;
                private String project_id;
                private String projectdetail_id;
                private String miyao;
                private int    id;
                private String id_pic;
                private String lanya;
                private String status;

                public String getFaddress() {
                    return faddress;
                }

                public void setFaddress(String faddress) {
                    this.faddress = faddress;
                }

                public String getFname() {
                    return fname;
                }

                public void setFname(String fname) {
                    this.fname = fname;
                }

                public String getXinxi() {
                    return xinxi;
                }

                public void setXinxi(String xinxi) {
                    this.xinxi = xinxi;
                }

                public String getAuthority_group_id() {
                    return authority_group_id;
                }

                public void setAuthority_group_id(String authority_group_id) {
                    this.authority_group_id = authority_group_id;
                }

                public String getProject_name() {
                    return project_name;
                }

                public void setProject_name(String project_name) {
                    this.project_name = project_name;
                }

                public String getCardno() {
                    return cardno;
                }

                public void setCardno(String cardno) {
                    this.cardno = cardno;
                }

                public String getRelation() {
                    return relation;
                }

                public void setRelation(String relation) {
                    this.relation = relation;
                }

                public String getLanyaid() {
                    return lanyaid;
                }

                public void setLanyaid(String lanyaid) {
                    this.lanyaid = lanyaid;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getProject_id() {
                    return project_id;
                }

                public void setProject_id(String project_id) {
                    this.project_id = project_id;
                }

                public String getProjectdetail_id() {
                    return projectdetail_id;
                }

                public void setProjectdetail_id(String projectdetail_id) {
                    this.projectdetail_id = projectdetail_id;
                }

                public String getMiyao() {
                    return miyao;
                }

                public void setMiyao(String miyao) {
                    this.miyao = miyao;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getId_pic() {
                    return id_pic;
                }

                public void setId_pic(String id_pic) {
                    this.id_pic = id_pic;
                }

                public String getLanya() {
                    return lanya;
                }

                public void setLanya(String lanya) {
                    this.lanya = lanya;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }
            }
        }
    }
}
