package controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import model.GameSave;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 存档控制器
public class SaveController {
    // 存档目录
    private static final String SAVE_DIR = "D:\\java\\JigsawPuzzles\\Game\\src\\main\\data\\save\\";

    // 保存游戏
    public static boolean saveGame(String username, int gridSize, int[][] puzzleState, 
                                   int emptyX, int emptyY, int stepCount, 
                                   String imagePath, int imageNum) {
        try {
            // 创建存档对象
            String saveTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            GameSave save = new GameSave(username, gridSize, puzzleState, emptyX, emptyY, 
                                         stepCount, imagePath, imageNum, saveTime);
            
            // 保存为JSON文件（按用户名命名）
            String filePath = SAVE_DIR + username + ".json";
            FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(save), filePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 读取存档
    public static GameSave loadGame(String username) {
        try {
            String filePath = SAVE_DIR + username + ".json";
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }
            String json = FileUtil.readUtf8String(filePath);
            if (StrUtil.isBlank(json)) {
                return null;
            }
            return JSONUtil.toBean(json, GameSave.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 检查是否有存档
    public static boolean hasSave(String username) {
        String filePath = SAVE_DIR + username + ".json";
        return new File(filePath).exists();
    }

    // 删除存档
    public static boolean deleteSave(String username) {
        try {
            String filePath = SAVE_DIR + username + ".json";
            return FileUtil.del(filePath);
        } catch (Exception e) {
            return false;
        }
    }
}
