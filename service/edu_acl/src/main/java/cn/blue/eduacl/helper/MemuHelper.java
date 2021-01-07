package cn.blue.eduacl.helper;

import cn.blue.eduacl.entity.Permission;
import cn.blue.eduacl.entity.PermissionPro;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 根据权限数据构建登录用户左侧菜单数据
 * </p>
 *
 * @author qy
 * @since 2019-11-11
 */
public class MemuHelper {

    /**
     * 构建菜单
     *
     * @param treeNodes
     * @return
     */
    public static List<JSONObject> buildAdmin(List<Permission> treeNodes) {
        List<JSONObject> meuns = new ArrayList<>();
        if (treeNodes.size() == 1) {
            Permission topNode = treeNodes.get(0);
            //左侧一级菜单
            List<Permission> oneMeunList = topNode.getChildren();
            for (Permission one : oneMeunList) {
                JSONObject oneMeun = new JSONObject();
                oneMeun.put("path", one.getPath());
                oneMeun.put("component", one.getComponent());
                oneMeun.put("redirect", "noredirect");
                oneMeun.put("name", "name_" + one.getId());
                oneMeun.put("hidden", false);

                JSONObject oneMeta = new JSONObject();
                oneMeta.put("title", one.getName());
                oneMeta.put("icon", one.getIcon());
                oneMeun.put("meta", oneMeta);

                List<JSONObject> children = new ArrayList<>();
                List<Permission> twoMeunList = one.getChildren();
                for (Permission two : twoMeunList) {
                    JSONObject twoMeun = new JSONObject();
                    twoMeun.put("path", two.getPath());
                    twoMeun.put("component", two.getComponent());
                    twoMeun.put("name", "name_" + two.getId());
                    twoMeun.put("hidden", false);

                    JSONObject twoMeta = new JSONObject();
                    twoMeta.put("title", two.getName());
                    twoMeun.put("meta", twoMeta);

                    children.add(twoMeun);

                    List<Permission> threeMeunList = two.getChildren();
                    for (Permission three : threeMeunList) {
                        if (StringUtils.isEmpty(three.getPath())) continue;

                        JSONObject threeMeun = new JSONObject();
                        threeMeun.put("path", three.getPath());
                        threeMeun.put("component", three.getComponent());
                        threeMeun.put("name", "name_" + three.getId());
                        threeMeun.put("hidden", true);

                        JSONObject threeMeta = new JSONObject();
                        threeMeta.put("title", three.getName());
                        threeMeun.put("meta", threeMeta);

                        children.add(threeMeun);
                    }
                }
                oneMeun.put("children", children);
                meuns.add(oneMeun);
            }
        }
        return meuns;
    }

    /**
     * 构建菜单
     *
     * @param treeNodes
     * @return
     */
    public static List<JSONObject> build(List<Permission> treeNodes) {
//        List<JSONObject> meuns = new ArrayList<>();
//        if (treeNodes.size() == 1) {
//            // 按理这个就是一级的，但是需要稍微封装下
//            Permission topNode = treeNodes.get(0);
//
//            JSONObject oneMeun1 = new JSONObject();
//            oneMeun1.put("path", topNode.getPath());
//            oneMeun1.put("component", topNode.getComponent());
//            oneMeun1.put("redirect", "noredirect");
//            oneMeun1.put("name", "name_" + topNode.getId());
//            oneMeun1.put("hidden", false);
//
//            JSONObject oneMeta1 = new JSONObject();
//            oneMeta1.put("title", topNode.getName());
//            oneMeta1.put("icon", topNode.getIcon());
//            oneMeun1.put("meta", oneMeta1);
//
//            //左侧二级菜单
//            List<Permission> oneMeunList = topNode.getChildren();
//            JSONObject oneMeun = new JSONObject();
//            for (Permission one : oneMeunList) {
//                oneMeun = new JSONObject();
//                oneMeun.put("path", one.getPath());
//                oneMeun.put("component", one.getComponent());
//                oneMeun.put("redirect", "noredirect");
//                oneMeun.put("name", "name_" + one.getId());
//                oneMeun.put("hidden", false);
//
//                JSONObject oneMeta = new JSONObject();
//                oneMeta.put("title", one.getName());
//                oneMeta.put("icon", one.getIcon());
//                oneMeun.put("meta", oneMeta);
//
//                List<JSONObject> children = new ArrayList<>();
//                List<Permission> twoMeunList = one.getChildren();
//                for (Permission two : twoMeunList) {
//                    JSONObject twoMeun = new JSONObject();
//                    twoMeun.put("path", two.getPath());
//                    twoMeun.put("component", two.getComponent());
//                    twoMeun.put("name", "name_" + two.getId());
//                    twoMeun.put("hidden", false);
//
//                    JSONObject twoMeta = new JSONObject();
//                    twoMeta.put("title", two.getName());
//                    twoMeun.put("meta", twoMeta);
//
//                    children.add(twoMeun);
//                }
//                oneMeun.put("children", children);
//            }
//            oneMeun1.put("children", oneMeun);
//            meuns.add(oneMeun1);
//        }
//        return meuns;
        List<PermissionPro> meuns = new ArrayList<>();
        if (treeNodes.size() == 1) {
            // 按理这个就是一级的，但是需要稍微封装下
            Permission topNode = treeNodes.get(0);

            PermissionPro oneMeun1 = new PermissionPro();
            oneMeun1.setPath(topNode.getPath());
            oneMeun1.setComponent(topNode.getComponent());
            oneMeun1.setRedirect("noredirect");
            oneMeun1.setName("name_" + topNode.getId());
            oneMeun1.setHidden(false);

//            JSONObject oneMeta1 = new JSONObject();
            List<Map<String, String>> meta = oneMeun1.getMeta();
            HashMap<String, String> map = new HashMap<>();
            map.put("title", topNode.getName());
            meta.set(0, map);
            map = new HashMap<>();
            map.put("icon", topNode.getIcon());
            meta.set(1, map);
            oneMeun1.setMeta(meta);

            //左侧二级菜单
            List<Permission> oneMeunList = topNode.getChildren();
            JSONObject oneMeun = new JSONObject();
            for (Permission one : oneMeunList) {
                oneMeun = new JSONObject();
                oneMeun.put("path", one.getPath());
                oneMeun.put("component", one.getComponent());
                oneMeun.put("redirect", "noredirect");
                oneMeun.put("name", "name_" + one.getId());
                oneMeun.put("hidden", false);

                JSONObject oneMeta = new JSONObject();
                oneMeta.put("title", one.getName());
                oneMeta.put("icon", one.getIcon());
                oneMeun.put("meta", oneMeta);

                List<JSONObject> children = new ArrayList<>();
                List<Permission> twoMeunList = one.getChildren();
                for (Permission two : twoMeunList) {
                    JSONObject twoMeun = new JSONObject();
                    twoMeun.put("path", two.getPath());
                    twoMeun.put("component", two.getComponent());
                    twoMeun.put("name", "name_" + two.getId());
                    twoMeun.put("hidden", false);

                    JSONObject twoMeta = new JSONObject();
                    twoMeta.put("title", two.getName());
                    twoMeun.put("meta", twoMeta);

                    children.add(twoMeun);
                }
                oneMeun.put("children", children);
            }
//            oneMeun1.put("children", oneMeun);
            meuns.add(oneMeun1);
        }
        return null;
    }
}
