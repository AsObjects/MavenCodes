package com.mccutil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


//import org.springframework.web.servlet.support.RequestContext;


import freemarker.core.Environment;
import freemarker.template.AdapterTemplateModel;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;

/**
 * Freemarker鏍囩宸ュ叿绫?
 * 
 * @author liufang
 * 
 */
public abstract class DirectiveUtils {
    /**
     * 杈撳嚭鍙傛暟锛氬璞℃暟鎹?
     */
    public static final String OUT_BEAN = "tag_bean";
    /**
     * 杈撳嚭鍙傛暟锛氬垪琛ㄦ暟鎹?
     */
    public static final String OUT_LIST = "tag_list";
    /**
     * 杈撳嚭鍙傛暟锛氬垎椤垫暟鎹?
     */
    public static final String OUT_PAGINATION = "tag_pagination";
    /**
     * 鍙傛暟锛氭槸鍚﹁皟鐢ㄦā鏉裤€?
     */
    public static final String PARAM_TPL = "tpl";
    /**
     * 鍙傛暟锛氭绾фā鏉垮悕绉?
     */
    public static final String PARAM_TPL_SUB = "tplSub";

    /**
     * 灏唒arams鐨勫€煎鍒跺埌variable涓?
     * 
     * @param env
     * @param params
     * @return 鍘烿ariable涓殑鍊?
     * @throws TemplateException
     */
    public static Map<String, TemplateModel> addParamsToVariable(
            Environment env, Map<String, TemplateModel> params)
            throws TemplateException {
        Map<String, TemplateModel> origMap = new HashMap<String, TemplateModel>();
        if (params.size() <= 0) {
            return origMap;
        }
        Set<Map.Entry<String, TemplateModel>> entrySet = params.entrySet();
        String key;
        TemplateModel value;
        for (Map.Entry<String, TemplateModel> entry : entrySet) {
            key = entry.getKey();
            value = env.getVariable(key);
            if (value != null) {
                origMap.put(key, value);
            }
            env.setVariable(key, entry.getValue());
        }
        return origMap;
    }

    /**
     * 灏唙ariable涓殑params鍊肩Щ闄?
     * 
     * @param env
     * @param params
     * @param origMap
     * @throws TemplateException
     */
    public static void removeParamsFromVariable(Environment env,
            Map<String, TemplateModel> params,
            Map<String, TemplateModel> origMap) throws TemplateException {
        if (params.size() <= 0) {
            return;
        }
        for (String key : params.keySet()) {
            env.setVariable(key, origMap.get(key));
        }
    }

    public static String getString(String name,
            Map<String, TemplateModel> params) throws TemplateException {
        TemplateModel model = params.get(name);
        if (model == null) {
            return null;
        }
        if (model instanceof TemplateScalarModel) {
            return ((TemplateScalarModel) model).getAsString();
        } else if ((model instanceof TemplateNumberModel)) {
            return ((TemplateNumberModel) model).getAsNumber().toString();
        } else {
        	System.out.println("必须是字符串");
        }
        return null;
    }

    public static Long getLong(String name, Map<String, TemplateModel> params)
            throws TemplateException {
        TemplateModel model = params.get(name);
        if (model == null) {
            return null;
        }
        if (model instanceof TemplateScalarModel) {
            String s = ((TemplateScalarModel) model).getAsString();
            if (StringUtils.isBlank(s)) {
                return null;
            }
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
            	System.out.println("必须是数字");
            }
        } else if (model instanceof TemplateNumberModel) {
            return ((TemplateNumberModel) model).getAsNumber().longValue();
        } else {
        	System.out.println("必须是数字");
        }
        return null;
    }

    public static Integer getInt(String name, Map<String, TemplateModel> params)
            throws TemplateException {
        TemplateModel model = params.get(name);
        if (model == null) {
            return null;
        }
        if (model instanceof TemplateScalarModel) {
            String s = ((TemplateScalarModel) model).getAsString();
            if (StringUtils.isBlank(s)) {
                return null;
            }
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
            	System.out.println("必须是数字");
            }
        } else if (model instanceof TemplateNumberModel) {
            return ((TemplateNumberModel) model).getAsNumber().intValue();
        } else {
            System.out.println("必须是数字");
        }
        return null;
    }

    public static Integer[] getIntArray(String name,
            Map<String, TemplateModel> params) throws TemplateException {
        String str = DirectiveUtils.getString(name, params);
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] arr = str.split(",");
        Integer[] ids = new Integer[arr.length];
        int i = 0;
        try {
            for (String s : arr) {
                ids[i++] = Integer.valueOf(s);
            }
            return ids;
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    public static Boolean getBool(String name, Map<String, TemplateModel> params)
            throws TemplateException {
        TemplateModel model = params.get(name);
        if (model == null) {
            return null;
        }
        if (model instanceof TemplateBooleanModel) {
            return ((TemplateBooleanModel) model).getAsBoolean();
        } else if (model instanceof TemplateNumberModel) {
            return !(((TemplateNumberModel) model).getAsNumber().intValue() == 0);
        } else if (model instanceof TemplateScalarModel) {
            String s = ((TemplateScalarModel) model).getAsString();
            // 绌轰覆搴旇杩斿洖null杩樻槸true鍛紵
            if (!StringUtils.isBlank(s)) {
                return !(s.equals("0") || s.equalsIgnoreCase("false") || s
                        .equalsIgnoreCase("f"));
            } else {
                return null;
            }
        } else {
            System.out.println("必须是布尔类型");
        }
        return null;
    }

    /**
     * 妯℃澘璋冪敤绫诲瀷
     * 
     * @author liufang
     */
    public enum InvokeType {
        body, custom, sysDefined, userDefined
    };

    /**
     * 鏄惁璋冪敤妯℃澘
     * 
     * 0锛氫笉璋冪敤锛屼娇鐢ㄦ爣绛剧殑body锛?锛氳皟鐢ㄨ嚜瀹氫箟妯℃澘锛?锛氳皟鐢ㄧ郴缁熼瀹氫箟妯℃澘锛?锛氳皟鐢ㄧ敤鎴烽瀹氫箟妯℃澘銆傞粯璁わ細0銆?
     * 
     * @param params
     * @return
     * @throws TemplateException
     */
    public static InvokeType getInvokeType(Map<String, TemplateModel> params)
            throws TemplateException {
        String tpl = getString(PARAM_TPL, params);
        if ("3".equals(tpl)) {
            return InvokeType.userDefined;
        } else if ("2".equals(tpl)) {
            return InvokeType.sysDefined;
        } else if ("1".equals(tpl)) {
            return InvokeType.custom;
        } else {
            return InvokeType.body;
        }
    }
}
