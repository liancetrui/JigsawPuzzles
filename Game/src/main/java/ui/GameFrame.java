package ui;

import util.ResourcePathUtil;
import util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class GameFrame extends JFrame implements KeyListener, ActionListener {
    public static final int LENGTH = 105; //图片的宽高

    //初始化数据
    int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    int[][] newArr = new int[4][4];
    // 拼图完成时的目标状态，0代表空白位置
    int[][] finalArr = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    // 记录空白图片(值为0)在二维数组newArr里面的行列位置
    int x = 0;
    int y = 0;
    
    // 图片缓存，避免每次移动都重新加载图片
    private ImageIcon[][] imageCache = new ImageIcon[4][4];
    private ImageIcon allImageCache = null;

    // 记录步数
    int stepCount = 0;

    //图片路径
    String pathAnimal = "image/animal/animal";
    String pathGirl = "image/girl/girl";
    String pathSport = "image/sport/sport";
    String path = pathAnimal;

    //创建图片对象
    JLabel background = new JLabel(new ImageIcon(getResourceUrl("image/background.png")));
    JLabel win = new JLabel(new ImageIcon(getResourceUrl("image/win.png")));
    JLabel about = new JLabel(new ImageIcon(getResourceUrl("image/about.png")));

    //创建更换图片
    JMenu changeImage = new JMenu("更换图片");

    //创建JMenuItem的对象
    JMenuItem girl = new JMenuItem("美女");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem sport = new JMenuItem("运动");
    // 创建步数标签
    JLabel stepLabel = new JLabel("步数: 0");

    //菜单的两个选项的对象
    JMenu funcJMenu = new JMenu("功能");
    JMenu aboutJMenu = new JMenu("关于我们");

    //创建选项的条目对象
    JMenuItem replayItem = new JMenuItem("重新开始");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("公众号");

    //创建一个随机数对象
    int randomNum = 1;
    
    // 不重复随机数生成器
    private List<Integer> animalNumbers;
    private List<Integer> girlNumbers;
    private List<Integer> sportNumbers;
    private int animalIndex = 0;
    private int girlIndex = 0;
    private int sportIndex = 0;

    public GameFrame() {
        // 初始化随机图片序列
        initRandomImageNumbers();
        // 初始化界面
        initJFrame();
        // 初始化菜单
        initJMenuBar();
        // 初始化数据(确保生成可解的拼图)
        initData();
        // 加载并缓存图片
        loadImageCache();
        // 初始化图片
        initImage();
        // 显示
        setVisible(true);
    }
    
    /**
     * 获取资源URL
     * @param path 资源路径
     * @return URL对象
     */
    private URL getResourceUrl(String path) {
        return ResourcePathUtil.getResourceUrl(path);
    }
    
    // 初始化不重复的随机图片序列
    private void initRandomImageNumbers() {
        // 动物图片: 1-8
        animalNumbers = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            animalNumbers.add(i);
        }
        Collections.shuffle(animalNumbers);
        
        // 美女图片: 1-12
        girlNumbers = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            girlNumbers.add(i);
        }
        Collections.shuffle(girlNumbers);
        
        // 运动图片: 1-10
        sportNumbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            sportNumbers.add(i);
        }
        Collections.shuffle(sportNumbers);
    }
    
    // 获取下一个不重复的动物图片编号
    private int getNextAnimalNumber() {
        if (animalIndex >= animalNumbers.size()) {
            Collections.shuffle(animalNumbers);
            animalIndex = 0;
        }
        return animalNumbers.get(animalIndex++);
    }
    
    // 获取下一个不重复的美女图片编号
    private int getNextGirlNumber() {
        if (girlIndex >= girlNumbers.size()) {
            Collections.shuffle(girlNumbers);
            girlIndex = 0;
        }
        return girlNumbers.get(girlIndex++);
    }
    
    // 获取下一个不重复的运动图片编号
    private int getNextSportNumber() {
        if (sportIndex >= sportNumbers.size()) {
            Collections.shuffle(sportNumbers);
            sportIndex = 0;
        }
        return sportNumbers.get(sportIndex++);
    }

    /**
     * 初始化拼图数据，确保生成的拼图是可解的
     */
    private void initData() {
        do {
            // 重置数组
            for (int i = 0; i < arr.length; i++) {
                arr[i] = i;
            }
            // 使用Fisher-Yates洗牌算法打乱数组
            Random r = new Random();
            for (int i = arr.length - 1; i > 0; i--) {
                int index = r.nextInt(i + 1);
                int t = arr[i];
                arr[i] = arr[index];
                arr[index] = t;
            }
            // 将一维数组转换为二维数组，并记录空白位置
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == 0) {
                    x = i / 4;
                    y = i % 4;
                }
                newArr[i / 4][i % 4] = arr[i];
            }
        } while (!isSolvable());  // 如果不可解，重新生成
    }
    
    /**
     * 检查当前拼图是否可解
     * 15数码问题可解性判断：逆序数 + 空白所在行数(从下往上数) 为偶数时可解
     */
    private boolean isSolvable() {
        int inversions = 0;
        // 计算逆序数(不包括空白块0)
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) continue;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] == 0) continue;
                if (arr[i] > arr[j]) {
                    inversions++;
                }
            }
        }
        // 空白块所在行(从下往上数，从1开始)
        int blankRowFromBottom = 4 - x;
        // 可解条件：逆序数 + 空白所在行数 为偶数
        return (inversions + blankRowFromBottom) % 2 == 0;
    }
    
    /**
     * 加载并缓存当前图片集的所有图片
     */
    private void loadImageCache() {
        // 创建默认的白色图片
        ImageIcon defaultImage = ImageUtil.createDefaultPuzzleImage();
        
        for (int num = 0; num <= 15; num++) {
            URL imageUrl = getResourceUrl(path + randomNum + "/" + num + ".jpg");
            if (imageUrl != null) {
                imageCache[num / 4][num % 4] = new ImageIcon(imageUrl);
            } else {
                // 如果图片不存在，使用默认白色图片
                imageCache[num / 4][num % 4] = defaultImage;
            }
        }
        
        URL allImageUrl = getResourceUrl(path + randomNum + "/" + "all.jpg");
        if (allImageUrl != null) {
            allImageCache = new ImageIcon(allImageUrl);
        } else {
            // 如果完整图片不存在，使用默认白色图片
            allImageCache = defaultImage;
        }
    }

    /**
     * 初始化/刷新拼图界面
     */
    private void initImage() {
        // 清空界面
        getContentPane().removeAll();
        if (win()) {
            initWin();
        }
        // 先加载的图片在上方
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // 获取图片编号
                int num = newArr[i][j];
                // 使用缓存的图片创建JLabel
                JLabel jLabel = new JLabel(imageCache[num / 4][num % 4]);
                // 指定图片位置
                jLabel.setBounds(LENGTH * j + 84, LENGTH * i + 134, LENGTH, LENGTH);
                // 设置图片的边框
                jLabel.setBorder(BorderFactory.createLineBorder(Color.black));
                // 把容器添加到界面中
                getContentPane().add(jLabel);
            }
        }
        // 后加载的图片在下方(背景)
        initBackground();
        // 显示步数
        initStepLabel();
        // 刷新界面
        getContentPane().repaint();
    }

    private void initWin() {
        win.setBounds(150, 200, 300, 300);
        getContentPane().add(win);
    }

    private void initBackground() {
        background.setBounds(40, 40, 508, 560);
        getContentPane().add(background);
    }

    private void initStepLabel() {
        stepLabel.setText("步数: " + stepCount);
        stepLabel.setBounds(400, 40, 150, 30);
        getContentPane().add(stepLabel);
    }

    private void initJMenuBar() {
        //初始化菜单
        JMenuBar jMenuBar = new JMenuBar();
        //4.把美女，动物，运动添加到更换图片当中
        changeImage.add(girl);
        changeImage.add(animal);
        changeImage.add(sport);


        //将选项下面的条目添加到选项中
        funcJMenu.add(changeImage);
        funcJMenu.add(replayItem);
        funcJMenu.add(reLoginItem);
        funcJMenu.add(closeItem);
        aboutJMenu.add(accountItem);

        //将菜单里面的选项添加到菜单中
        jMenuBar.add(funcJMenu);
        jMenuBar.add(aboutJMenu);

        //给这个界面设置菜单
        setJMenuBar(jMenuBar);
        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);

        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
    }

    private void initJFrame() {
        //宽高
        setSize(603, 680);
        //标题
        setTitle("拼图游戏 v1.0");
        //置顶
        setAlwaysOnTop(true);
        //居中
        setLocationRelativeTo(null);
        //关闭模式
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认的居中放置
        setLayout(null);
        //给整个界面添加键盘监听事件
        this.addKeyListener(this);
        //禁止改变界面大小
        setResizable(false);
        //禁用输入法
        this.enableInputMethods(false);
    }

    private boolean win() {
        for (int i = 0; i < newArr.length; i++) {
            for (int j = 0; j < newArr[i].length; j++) {
                if (newArr[i][j] != finalArr[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!win()) {
            int keyCode = e.getKeyCode();
            // 按A键显示完整图片
            if (keyCode == 65) {
                getContentPane().removeAll();
                JLabel allJLabel = new JLabel(allImageCache);
                allJLabel.setBounds(84, 134, 420, 420);
                add(allJLabel);
                add(background);
                getContentPane().repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!win()) {
            // 方向键: 左37 上38 右39 下40
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case 37 -> {  // 左
                    if (y < 3) {
                        newArr[x][y] = newArr[x][y + 1];
                        newArr[x][y + 1] = 0;
                        y++;
                        stepCount++;
                        initImage();
                    }
                }
                case 38 -> {  // 上
                    if (x < 3) {
                        newArr[x][y] = newArr[x + 1][y];
                        newArr[x + 1][y] = 0;
                        x++;
                        stepCount++;
                        initImage();
                    }
                }
                case 39 -> {  // 右
                    if (y > 0) {
                        newArr[x][y] = newArr[x][y - 1];
                        newArr[x][y - 1] = 0;
                        y--;
                        stepCount++;
                        initImage();
                    }
                }
                case 40 -> {  // 下
                    if (x > 0) {
                        newArr[x][y] = newArr[x - 1][y];
                        newArr[x - 1][y] = 0;
                        x--;
                        stepCount++;
                        initImage();
                    }
                }
                case 65 -> initImage();  // A键释放时恢复拼图
                case 87 -> {  // W键一键通关(调试用)
                    for (int i = 0; i < newArr.length; i++) {
                        for (int j = 0; j < newArr[i].length; j++) {
                            newArr[i][j] = finalArr[i][j];
                        }
                    }
                    x = 3;
                    y = 3;
                    initImage();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // 更换图片类型
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
        }

        // 功能菜单操作
        if (source == replayItem) {
            // 重新开始：重置步数并生成新的拼图
            stepCount = 0;
            initData();
            initImage();
        } else if (source == reLoginItem) {
            dispose();  // 销毁游戏窗口，释放资源
            new LoginFrame();

        } else if (source == closeItem) {
            System.exit(0);

        } else if (source == accountItem) {
            JDialog jDialog = new JDialog();
            // 标题设置
            jDialog.setTitle("公众号");
            // 图片设置
            about.setBounds(0, 0, 258, 258);
            // 宽高
            jDialog.setSize(344, 344);
            // 置顶
            jDialog.setAlwaysOnTop(true);
            // 居中
            jDialog.setLocationRelativeTo(null);
            // 不关闭无法操作下面的界面
            jDialog.setModal(true);
            // 添加图片
            jDialog.getContentPane().add(about);
            // 显示
            jDialog.setVisible(true);
        }

    }
}