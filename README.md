# ThreeRatingBar
自定义笑脸评分条

### attrs.xml
    <!--星级自定义属性-->
    <declare-styleable name="ThirdRatingBarView">
        <attr name="dividerWidth" format="dimension" />//间距
        <attr name="default_icon" format="reference" />//默认图标
        <attr name="selection_icon_bad" format="reference" />//差评图标
        <attr name="selection_icon_good" format="reference" />//好评图标
        <attr name="selection_icon_half_bad" format="reference" />//半星差图标
        <attr name="selection_icon_half_good" format="reference" />//半星好图标
        <attr name="starImageSize" format="float" />//星星大小
        <attr name="starCount" format="integer" />//星星数量
        <attr name="rating" format="float" />//当前评级
    </declare-styleable>
### xml引用
    <com.yuanli.threeratingbar.widget.ThreeLevelRatingBarView
        android:id="@+id/ratingTotal"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_marginLeft="20dp"
        app:default_icon="@mipmap/evaluate_default_face"
        app:rating="1.36"
        android:layout_toRightOf="@+id/tv_1"
        app:selection_icon_bad="@drawable/rating_bar_bad_smile"
        app:selection_icon_good="@drawable/rating_bar"
        app:selection_icon_half_good="@mipmap/evaluate_yellow_half"
        app:selection_icon_half_bad="@mipmap/evaluate_bad_face_half"
        app:starCount="5"
        app:starImageSize="96"/>
