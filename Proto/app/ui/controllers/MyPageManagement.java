package ui.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import ui.business.MyPageManager;
import ui.models.MyPageSetting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/***
 * サンプル用ベースフレームコントローラー.
 * 
 * @author CTC
 * 
 */
public class MyPageManagement extends Controller {

    private static Form<MyPageSetting> subframeSettingForm = Form
            .form(MyPageSetting.class);

    /***
     * マイページ設定の取得
     * 
     * @return Result
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result load() {
        JsonNode rootNode = request().body().asJson();
        System.out.println("引数はこんな感じです");
        System.out.println(rootNode);

        String userId = rootNode.get("userId").textValue();
        String settingId = (rootNode.get("settingId") == null) ? null
                : rootNode.get("settingId").textValue();
        String kinouId = (rootNode.get("kinouId") == null) ? null : rootNode
                .get("kinouId").textValue();

        // ダミーデータ登録
        // MyPageSetting setting1;
        // for (int i = 0; i < 3; i++) {
        // setting1 = new MyPageSetting();
        // setting1.userId = userId;
        // setting1.settingId = "default";
        // setting1.kinouId = "kinou1";
        // setting1.subframeId = "subframe" + i;
        // setting1.x = 100;
        // setting1.y = 100;
        // setting1.zindex = 100;
        // setting1.width = 100;
        // setting1.height = 100;
        // setting1.visiblity = "visible";
        //
        // setting1.save();
        // }

        System.out.println("マイページ設定を取得します");
        System.out.println(String.format(
                "user : %s     kinou : %s    settingId : %s", userId, kinouId,
                settingId));

        List<MyPageSetting> settings = MyPageManager.load(userId, settingId,
                kinouId);

        System.out.println("取得できた設定は");
        System.out.println(String.format("%d 件です。", settings.size()));

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = Json.newObject();

        try {
            System.out.println("Json文字列に変換します");
            Map<String, Map<String, Map<String, MyPageSetting>>> resMap = new HashMap<String, Map<String, Map<String, MyPageSetting>>>();

            for (MyPageSetting setting : settings) {
                String resSettingId = setting.settingId;
                String resKinouId = setting.kinouId;
                if (!resMap.containsKey(resSettingId)) {

                    resMap.put(resSettingId,
                            new HashMap<String, Map<String, MyPageSetting>>());
                }

                if (!resMap.get(resSettingId).containsKey(resKinouId)) {
                    resMap.get(resSettingId).put(resKinouId,
                            new HashMap<String, MyPageSetting>());
                }

                resMap.get(resSettingId).get(resKinouId)
                        .put(setting.subframeId, setting);
                System.out.println("設定はこんな：" + setting.userId);
            }

            result.put("settings", mapper.writeValueAsString(resMap));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result.put("status", "NG");
            return internalServerError(result);
        }

        System.out.println(result);

        return ok(result);
    }

    /***
     * 
     * マイページ設定の保存
     * 
     * @return Result
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result save() {
        JsonNode rootNode = request().body().asJson();
        ObjectNode result = Json.newObject();
        System.out.println("引数はこんな感じです");
        System.out.println(rootNode);

        List<MyPageSetting> settings = new ArrayList<MyPageSetting>();

        // すべての設定を保存用フォームオブジェクトに移す。
        JsonNode jnSettings = rootNode.get("settings");
        JsonNode current;
        int i = 0;
        current = jnSettings.get(i);
        while (current != null) {
            System.out.println(current);
            Map<String, String> mapj = new HashMap<String, String>();
            Iterator<String> fields = current.fieldNames();

            while (fields.hasNext()) {
                String field = fields.next();
                String value = current.get(field).textValue();
                if (value == null) {
                    value = current.get(field).toString();
                }
                if ("updateAt".equals(field)) {
                    Date d = new Date(Long.parseLong(value));
                    mapj.put(field, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                            .format(d));
                } else {
                    mapj.put(field, value);
                }

            }
            System.out.println("マップ作成:" + mapj);

            Form<MyPageSetting> settingForm = subframeSettingForm.bind(mapj);
            System.out.println("フォームへ格納");

            if (settingForm.hasErrors()) {
                System.out.println(settingForm.errors());
                result = Json.newObject();
                result.put("status", "NG");
                return badRequest(current);
            }

            settings.add(settingForm.get());

            i++;
            current = jnSettings.get(i);
        }

        System.out.println("マイページ設定を保存します。");
        MyPageManager.save(settings);

        System.out.println("再読み込みするよ");
        System.out.println(rootNode);

        String userId = rootNode.get("userId").textValue();
        String settingId = (rootNode.get("settingId") == null) ? null
                : rootNode.get("settingId").textValue();
        String kinouId = (rootNode.get("kinouId") == null) ? null : rootNode
                .get("kinouId").textValue();

        System.out.println("マイページ設定を取得します");
        System.out.println(String.format(
                "user : %s     kinou : %s    settingId : %s", userId, kinouId,
                settingId));

        settings = MyPageManager.load(userId, settingId, kinouId);

        System.out.println("取得できた設定は");
        System.out.println(String.format("%d 件です。", settings.size()));

        ObjectMapper mapper = new ObjectMapper();

        try {
            System.out.println("Json文字列に変換します");
            Map<String, Map<String, Map<String, MyPageSetting>>> resMap = new HashMap<String, Map<String, Map<String, MyPageSetting>>>();

            for (MyPageSetting setting : settings) {
                String resSettingId = setting.settingId;
                String resKinouId = setting.kinouId;
                if (!resMap.containsKey(resSettingId)) {

                    resMap.put(resSettingId,
                            new HashMap<String, Map<String, MyPageSetting>>());
                }

                if (!resMap.get(resSettingId).containsKey(resKinouId)) {
                    resMap.get(resSettingId).put(resKinouId,
                            new HashMap<String, MyPageSetting>());
                }

                resMap.get(resSettingId).get(resKinouId)
                        .put(setting.subframeId, setting);
                System.out.println("設定はこんな：" + setting.userId);
            }

            result.put("settings", mapper.writeValueAsString(resMap));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result.put("status", "NG");
            return internalServerError(result);
        }

        System.out.println(result);
        result.put("status", "OK");
        return ok(result);
    }
}
