package com.fuwo.b3d.designcase.model;

import com.fuwo.b3d.enums.StatusEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "3d_case")
@Entity
public class Case implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
     //案例编号
    @Column(name = "case_no")
    private String caseNo;
    //案例名称
    @Column(name = "name")
    private String name;
   //设计编号
    @Column(name = "design_no")
    private String designNo;
    //设计风格
    @Column(name = "design_style")
    private DesignStyleEnum designStyle;
    //户型
    @Column(name = "house_type")
    private HouseTypeEnum houseType;
    //空间
    @Column(name = "space_type")
    private SpaceTypeEnum spaceType;
    //面积
    @Column(name = "area_type")
    private  AreaTypeEnum areaType;
    //全景图链接
    @Column(name = "url")
    private String url;
    //是否公开
    @Column(name = "open")
    private boolean open;
    //是否可售
    @Column(name = "sale")
    private boolean sale;
    //价格
    @Column(name = "price")
    private Integer price;
    //描述
    @Column(name = "description")
    private String description;
    //封面url
    @Column(name = "cover_url")
    private  String coverUrl;


    @Column(name = "creator")
    private String creator;

    @Column(name = "modifier")
    private String modifier;

    @Column(name = "created")
    private Date created;

    @Column(name = "modified")
    private Date modified;

    @Column(name = "status")
    private StatusEnum status = StatusEnum.ENABLE;

    //点赞
    @Column(name = "likes")
    private Long likes ;
    //收藏
    @Column(name = "collection")
    private Long collection;
    //评论
    @Column(name = "comment")
    private Long comment;

    public  static final String OPEN="公开";

    public static final String NOT_OPEN="不公开";

    public enum DesignStyleEnum {

        JY ("0", "简约"),
        XD("1","现代"),
        ZS("2", "中式"),
        OS("3", "欧式"),
        MS("4", "美式"),
        TY("5", "田园"),
        XGD("6", "新古典"),
        HD("7", "混搭"),
        DZH ("8", "地中海"),
        DNY("9","东南亚"),
        RS ("10", "日式"),
        YJ ("11", "宜家"),
        ;

        private String desc;

        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        DesignStyleEnum(String code,String desc ) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum openEnum {

        QB("0", "全部"),
        GK("1", "公开"),
        BGK("2", "不公开"),
        ;

        private String code;
        private String desc;
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        openEnum(String code,String desc ) {
            this.code = code;
            this.desc = desc;
        }
    }


    //        户型
    public enum  HouseTypeEnum {
        XHX ("0", "小户型"),
        ONE  ("1", "一居"),
        TWO  ("2", "两居"),
        THREE ("3", "三居"),
        FOUR ("4", "四居"),
        FS ("5", "复式"),
        BS ("6", "别墅"),
        GY("7", "公寓"),
        ;

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        HouseTypeEnum( String code,String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    //        空间
    public enum  SpaceTypeEnum {
         KT("0", "客厅"),
        WS   ("1", "卧室"),
        CT ("2", "餐厅"),
        CF ("3", "厨房"),
        WYJ ("4", "卫浴间"),
        SF ("5", "书房"),
        XG ("6", "玄关"),
        ETF("7", "儿童房"),
        YT ("8", "阳台"),
        YMJ ("9", "衣帽间"),
        HY("10", "花园"),
        ;


        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        SpaceTypeEnum(String code,String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    //        面积
    public enum  AreaTypeEnum {
        WS("0", "50平以下"),
        QS ("1", "50-70m2"),
        JS ("2", "70-90m2"),
        YBE ("3", "90-120m2"),
        YBW  ("4", "120-150m2"),
        LB("5", "150-200m2"),
        GLB ("6", "200m2以上"),

        ;

        private String desc;

        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

         AreaTypeEnum( String code,String desc) {
             this.code = code;
             this.desc = desc;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignNo() {
        return designNo;
    }

    public void setDesignNo(String designNo) {
        this.designNo = designNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public DesignStyleEnum getDesignStyle() {
        return designStyle;
    }

    public void setDesignStyle(DesignStyleEnum designStyle) {
        this.designStyle = designStyle;
    }

    public HouseTypeEnum getHouseType() {
        return houseType;
    }

    public void setHouseType(HouseTypeEnum houseType) {
        this.houseType = houseType;
    }

    public SpaceTypeEnum getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(SpaceTypeEnum spaceType) {
        this.spaceType = spaceType;
    }

    public AreaTypeEnum getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaTypeEnum areaType) {
        this.areaType = areaType;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getCollection() {
        return collection;
    }

    public void setCollection(Long collection) {
        this.collection = collection;
    }

    public Long getComment() {
        return comment;
    }

    public void setComment(Long comment) {
        this.comment = comment;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }
}
