package model;

// 游戏存档模型
public class GameSave {
    // 用户名
    private String username;
    // 难度（网格大小）
    private int gridSize;
    // 当前拼图状态
    private int[][] puzzleState;
    // 空白块位置
    private int emptyX;
    private int emptyY;
    // 步数
    private int stepCount;
    // 图片类型
    private String imagePath;
    // 图片编号
    private int imageNum;
    // 保存时间
    private String saveTime;

    // 无参构造
    public GameSave() {}

    // 全参构造
    public GameSave(String username, int gridSize, int[][] puzzleState, int emptyX, int emptyY, 
                    int stepCount, String imagePath, int imageNum, String saveTime) {
        this.username = username;
        this.gridSize = gridSize;
        this.puzzleState = puzzleState;
        this.emptyX = emptyX;
        this.emptyY = emptyY;
        this.stepCount = stepCount;
        this.imagePath = imagePath;
        this.imageNum = imageNum;
        this.saveTime = saveTime;
    }

    // Getter & Setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getGridSize() { return gridSize; }
    public void setGridSize(int gridSize) { this.gridSize = gridSize; }

    public int[][] getPuzzleState() { return puzzleState; }
    public void setPuzzleState(int[][] puzzleState) { this.puzzleState = puzzleState; }

    public int getEmptyX() { return emptyX; }
    public void setEmptyX(int emptyX) { this.emptyX = emptyX; }

    public int getEmptyY() { return emptyY; }
    public void setEmptyY(int emptyY) { this.emptyY = emptyY; }

    public int getStepCount() { return stepCount; }
    public void setStepCount(int stepCount) { this.stepCount = stepCount; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public int getImageNum() { return imageNum; }
    public void setImageNum(int imageNum) { this.imageNum = imageNum; }

    public String getSaveTime() { return saveTime; }
    public void setSaveTime(String saveTime) { this.saveTime = saveTime; }
}
