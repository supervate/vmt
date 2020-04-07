package vt.vmt.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import vt.vmt.entity.Module;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static vt.vmt.VmtConstant.*;

/**
 * @author vate
 * date: 2020/4/6 16:08
 */
@Controller
@RequestMapping("/" + URI_PREFIX_VMT)
public class IndexController {

    /**
     * this map contains module name and its extra information.
     */
    private static List<Module> modules = new ArrayList<>();

    @RequestMapping
    public String index(ModelMap mmp){
        mmp.put("modules", modules);
        return "vmt/index/index.html";
    }

    public static void registModule(Module module){
        modules.add(module);
        modules.sort(Comparator.comparingInt(Module::getOrder));
    }
}
