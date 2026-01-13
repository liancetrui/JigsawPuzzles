package ui;

import util.ImageUtil;
import util.ResourcePathUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// 游戏主界面
public class GameFrame extends JFrame implements KeyListener, ActionListener {
    // 拼图块尺寸
    public static final int LENGTH = 105;

    // VIP用户名
    private String currentUsername;
    private static final String VIP_USERNAME = "Narylr";

    // 拼图数据
    int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    int[][] newArr = new int[4][4];
    // 目标状态
    int[][] finalArr = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    // 空白位置
    int x = 0;
    int y = 0;

    // 图片缓存
    private ImageIcon[][] imageCache = new ImageIcon[4][4];
    private ImageIcon allImageCache = null;

    // 步数
    int stepCount = 0;

    // 图片路径
    String pathAnimal = "image/animal/animal";
    String pathGirl = "image/girl/girl";
    String pathSport = "image/sport/sport";
    String pathPerson = "image/person/person";
    String path = pathAnimal;

    // 图片组件
    JLabel background = new JLabel(new ImageIcon(getResourceUrl("image/background.png")));
    JLabel win = new JLabel(new ImageIcon(getResourceUrl("image/win.png")));
    JLabel about = new JLabel(new ImageIcon(getResourceUrl("image/about.png")));

    // 更换图片菜单
    JMenu changeImage = new JMenu("更换图片");

    // 图片选项
    JMenuItem girl = new JMenuItem("美女");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem sport = new JMenuItem("运动");
    JMenuItem person = new JMenuItem("人");
    // 步数标签
    JLabel stepLabel = new JLabel("步数: 0");

    // 菜单选项
    JMenu funcJMenu = new JMenu("功能");
    JMenu aboutJMenu = new JMenu("关于我们");

    // 菜单条目
    JMenuItem replayItem = new JMenuItem("重新开始");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("公众号");

    // 随机图片编号
    int randomNum = 1;

    // 随机图片序列
    private List<Integer> animalNumbers;
    private List<Integer> girlNumbers;
    private List<Integer> sportNumbers;
    private List<Integer> personNumbers;
    private int personIndex = 0;
    private int animalIndex = 0;
    private int girlIndex = 0;
    private int sportIndex = 0;

    // 默认构造方法
    public GameFrame() {
        this(null);
    }

    // 带用户名的构造方法
    public GameFrame(String username) {
        this.currentUsername = username;
        initRandomImageNumbers();
        initJFrame();
        initJMenuBar();
        initData();
        loadImageCache();
        initImage();
        setVisible(true);
    }

    // 初始化窗口
    private void initJFrame() {
        setSize(603, 680);
        setTitle("拼图游戏 v1.0");
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        addKeyListener(this);
        setResizable(false);
        enableInputMethods(false);
    }

    // 获取资源URL
    private URL getResourceUrl(String path) {
        return ResourcePathUtil.getResourceUrl(path);
    }

    // 初始化随机图片序列
    private void initRandomImageNumbers() {
        // 动物图片
        animalNumbers = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            animalNumbers.add(i);
        }
        Collections.shuffle(animalNumbers);

        // 美女图片
        girlNumbers = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            girlNumbers.add(i);
        }
        Collections.shuffle(girlNumbers);

        // 运动图片
        sportNumbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            sportNumbers.add(i);
        }
        Collections.shuffle(sportNumbers);

        // 人物图片
        personNumbers = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            personNumbers.add(i);
        }
        Collections.shuffle(personNumbers);
    }

    // 获取下一个人物图片编号
    private int getNextPersonNumber() {
        if (personIndex >= personNumbers.size()) {
            Collections.shuffle(personNumbers);
            personIndex = 0;
        }
        return personNumbers.get(personIndex++);
    }

    // 获取下一个动物图片编号
    private int getNextAnimalNumber() {
        if (animalIndex >= animalNumbers.size()) {
            Collections.shuffle(animalNumbers);
            animalIndex = 0;
        }
        return animalNumbers.get(animalIndex++);
    }

    // 获取下一个美女图片编号
    private int getNextGirlNumber() {
        if (girlIndex >= girlNumbers.size()) {
            Collections.shuffle(girlNumbers);
            girlIndex = 0;
        }
        return girlNumbers.get(girlIndex++);
    }

    // 获取下一个运动图片编号
    private int getNextSportNumber() {
        if (sportIndex >= sportNumbers.size()) {
            Collections.shuffle(sportNumbers);
            sportIndex = 0;
        }
        return sportNumbers.get(sportIndex++);
    }

    // 初始化拼图数据
    private void initData() {
        do {
            // 重置数组
            for (int i = 0; i < arr.length; i++) {
                arr[i] = i;
            }
            // Fisher-Yates洗牌
            Random r = new Random();
            for (int i = arr.length - 1; i > 0; i--) {
                int index = r.nextInt(i + 1);
                int t = arr[i];
                arr[i] = arr[index];
                arr[index] = t;
            }
            // 转换为二维数组
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == 0) {
                    x = i / 4;
                    y = i % 4;
                }
                newArr[i / 4][i % 4] = arr[i];
            }
        } while (!isSolvable());
    }

    // 检查拼图是否可解
    private boolean isSolvable() {
        int inversions = 0;
        // 计算逆序数
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                continue;
            }
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] == 0) {
                    continue;
                }
                if (arr[i] > arr[j]) {
                    inversions++;
                }
            }
        }
        // 空白块所在行
        int blankRowFromBottom = 4 - x;
        // 可解条件
        return (inversions + blankRowFromBottom) % 2 == 0;
    }

    // 加载并缓存图片
    private void loadImageCache() {
        // 创建默认白色图片
        ImageIcon defaultImage = ImageUtil.createDefaultPuzzleImage();

        for (int num = 0; num <= 15; num++) {
            URL imageUrl = getResourceUrl(path + randomNum + "/" + num + ".jpg");
            if (imageUrl != null) {
                imageCache[num / 4][num % 4] = new ImageIcon(imageUrl);
            } else {
                imageCache[num / 4][num % 4] = defaultImage;
            }
        }

        URL allImageUrl = getResourceUrl(path + randomNum + "/" + "all.jpg");
        if (allImageUrl != null) {
            allImageCache = new ImageIcon(allImageUrl);
        } else {
            allImageCache = defaultImage;
        }
    }

    // 初始化拼图界面
    private void initImage() {
        // 清空界面
        getContentPane().removeAll();
        if (win()) {
            initWin();
        }
        // 添加拼图块
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = newArr[i][j];
                JLabel jLabel = new JLabel(imageCache[num / 4][num % 4]);
                jLabel.setBounds(LENGTH * j + 84, LENGTH * i + 134, LENGTH, LENGTH);
                jLabel.setBorder(BorderFactory.createLineBorder(Color.black));
                
                // 点击事件
                final int row = i;
                final int col = j;
                jLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (win()) return;
                        clickPuzzle(row, col);
                    }
                });
                
                getContentPane().add(jLabel);
            }
        }
        // 背景
        initBackground();
        // 步数
        initStepLabel();
        // 刷新
        getContentPane().repaint();
    }
    
    // 点击拼图块与空白块交换
    private void clickPuzzle(int row, int col) {
        // 判断是否相邻
        if ((Math.abs(row - x) == 1 && col == y) || (Math.abs(col - y) == 1 && row == x)) {
            // 交换
            newArr[x][y] = newArr[row][col];
            newArr[row][col] = 0;
            x = row;
            y = col;
            stepCount++;
            initImage();
        }
    }

    // 显示胜利图片
    private void initWin() {
        win.setBounds(150, 200, 300, 300);
        getContentPane().add(win);
    }

    // 初始化背景
    private void initBackground() {
        background.setBounds(40, 40, 508, 560);
        getContentPane().add(background);
    }

    // 初始化步数标签
    private void initStepLabel() {
        stepLabel.setText("步数: " + stepCount);
        stepLabel.setBounds(400, 40, 150, 30);
        getContentPane().add(stepLabel);
    }

    // 初始化菜单栏
    private void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        // 添加图片选项
        changeImage.add(girl);
        changeImage.add(animal);
        changeImage.add(sport);
        changeImage.add(person);

        // 添加菜单条目
        funcJMenu.add(changeImage);
        funcJMenu.add(replayItem);
        funcJMenu.add(reLoginItem);
        funcJMenu.add(closeItem);
        aboutJMenu.add(accountItem);

        // 添加菜单
        jMenuBar.add(funcJMenu);
        jMenuBar.add(aboutJMenu);

        setJMenuBar(jMenuBar);
        // 添加监听器
        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);
        person.addActionListener(this);

        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
    }

    // 检查是否胜利
    private boolean win() {
        for (int i = 0; i < newArr.length; i++) {
            for (int j = 0; j < newArr[i].length; j++) {
                if (newArr[i][j] != finalArr[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // 键盘输入事件
    @Override
    public void keyTyped(KeyEvent e) {

    }

    // 键盘按下事件
    @Override
    public void keyPressed(KeyEvent e) {
        if (win()) {
            return;
        }

        int keyCode = e.getKeyCode();
        // A键显示完整图片
        if (keyCode == 65) {
            getContentPane().removeAll();
            JLabel allJLabel = new JLabel(allImageCache);
            allJLabel.setBounds(84, 134, 420, 420);
            add(allJLabel);
            add(background);
            getContentPane().repaint();
        }
    }

    // 键盘释放事件
    @Override
    public void keyReleased(KeyEvent e) {
        if (win()) {
            return;
        }

        // 方向键控制
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case 37 -> {
                // 左
                if (y < 3) {
                    newArr[x][y] = newArr[x][y + 1];
                    newArr[x][y + 1] = 0;
                    y++;
                    stepCount++;
                    initImage();
                }
            }
            case 38 -> {
                // 上
                if (x < 3) {
                    newArr[x][y] = newArr[x + 1][y];
                    newArr[x + 1][y] = 0;
                    x++;
                    stepCount++;
                    initImage();
                }
            }
            case 39 -> {
                // 右
                if (y > 0) {
                    newArr[x][y] = newArr[x][y - 1];
                    newArr[x][y - 1] = 0;
                    y--;
                    stepCount++;
                    initImage();
                }
            }
            case 40 -> {
                // 下
                if (x > 0) {
                    newArr[x][y] = newArr[x - 1][y];
                    newArr[x - 1][y] = 0;
                    x--;
                    stepCount++;
                    initImage();
                }
            }
            // A键恢复拼图
            case 65 -> initImage();
            case 87 -> {
                // W键一键通关(VIP专属)
                if (VIP_USERNAME.equals(currentUsername)) {
                    for (int i = 0; i < newArr.length; i++) {
                        System.arraycopy(finalArr[i], 0, newArr[i], 0, newArr[i].length);
                    }
                    x = 3;
                    y = 3;
                    initImage();
                }
            }
        }
    }

    // 菜单点击事件
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // 更换图片
        if (source == animal) {
            randomNum = getNextAnimalNumber();
            stepCount = 0;
            path = pathAnimal;
            initData();
            loadImageCache();
            initImage();
        } else if (source == girl) {
            randomNum = getNextGirlNumber();
            stepCount = 0;
            path = pathGirl;
            initData();
            loadImageCache();
            initImage();
        } else if (source == sport) {
            randomNum = getNextSportNumber();
            stepCount = 0;
            path = pathSport;
            initData();
            loadImageCache();
            initImage();
        } else if (source == person) {
            randomNum = getNextPersonNumber();
            stepCount = 0;
            path = pathPerson;
            initData();
            loadImageCache();
            initImage();
        }

        // 功能菜单
        if (source == replayItem) {
            // 重新开始
            stepCount = 0;
            initData();
            initImage();
        } else if (source == reLoginItem) {
            // 重新登录
            dispose();
            new LoginFrame();

        } else if (source == closeItem) {
            // 关闭游戏
            System.exit(0);

        } else if (source == accountItem) {
            // 关于我们
            JDialog jDialog = new JDialog();
            jDialog.setTitle("公众号");
            about.setBounds(0, 0, 258, 258);
            jDialog.setSize(344, 344);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            jDialog.setModal(true);
            jDialog.getContentPane().add(about);
            jDialog.setVisible(true);
        }

    }
}