package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


public class GameFrame extends JFrame implements KeyListener {
    public static final int LENGTH = 105; //图片的宽高

    //初始化数据
    int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    int[][] newArr = new int[4][4];

    //记录空白图片在二维数组newArr里面的位置
    int x = 0;
    int y = 0;

    public GameFrame() {
        //初始化界面
        initJFrame();
        //初始化菜单
        initJMenuBar();
        //初始化数据
        initData();
        //初始化图片
        initImage();
        //显示
        setVisible(true);
    }

    private void initData() {
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            int index = r.nextInt(arr.length);
            int t = arr[i];
            arr[i] = arr[index];
            arr[index] = t;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                x = i / 4;
                y = i % 4;
            } else {
                newArr[i / 4][i % 4] = arr[i];
            }
        }
    }

    private void initImage() {
        //清空界面
        getContentPane().removeAll();
        //先加载的图片在上方
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //获取图片编号
                int num = newArr[i][j];
                //创建JLabel对象
                JLabel jLabel = new JLabel(new ImageIcon("Game\\src\\main\\resources\\image\\animal\\animal3\\" + num + ".jpg"));
                //指定图片位置
                jLabel.setBounds(LENGTH * j + 84, LENGTH * i + 134, LENGTH, LENGTH);
                //设置图片的边框
                //jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                jLabel.setBorder(BorderFactory.createLineBorder(Color.black));
                //把容器添加到界面中
                add(jLabel);
            }
        }
        //后加载的图片在下方
        //背景
        ImageIcon icon = new ImageIcon("Game\\src\\main\\resources\\image\\background.png");
        JLabel background = new JLabel(icon);
        background.setBounds(40, 40, 508, 560);
        add(background);

        //刷新界面
        getContentPane().repaint();
    }

    private void initJMenuBar() {
        //初始化菜单
        JMenuBar jMenuBar = new JMenuBar();

        //菜单的两个选项的对象
        JMenu funcJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");

        //创建选项的条目对象
        JMenuItem replayItem = new JMenuItem("重新开始");
        JMenuItem reLoginItem = new JMenuItem("重新登录");
        JMenuItem closeItem = new JMenuItem("关闭游戏");

        JMenuItem accountItem = new JMenuItem("公众号");

        //将选项下面的条目添加到选项中
        funcJMenu.add(replayItem);
        funcJMenu.add(reLoginItem);
        funcJMenu.add(closeItem);

        aboutJMenu.add(accountItem);

        //将菜单里面的选项添加到菜单中
        jMenuBar.add(funcJMenu);
        jMenuBar.add(aboutJMenu);

        //给这个界面设置菜单
        setJMenuBar(jMenuBar);
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
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //左：37 上：38 右：39 下：40
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case 37:
                System.out.println("向左移动");
                if (y < 3) {
                    newArr[x][y] = newArr[x][y + 1];
                    newArr[x][y + 1] = 0;
                    y++;
                    initImage();
                }
                break;
            case 38:
                System.out.println("向上移动");
                if (x < 3) {
                    newArr[x][y] = newArr[x + 1][y];
                    newArr[x + 1][y] = 0;
                    x++;
                    initImage();
                }
                break;
            case 39:
                System.out.println("向右移动");
                if (y > 0) {
                    newArr[x][y] = newArr[x][y - 1];
                    newArr[x][y - 1] = 0;
                    y--;
                    initImage();
                }
                break;
            case 40:
                System.out.println("向下移动");
                if (x > 0) {
                    newArr[x][y] = newArr[x - 1][y];
                    newArr[x - 1][y] = 0;
                    x--;
                    initImage();
                }
                break;
        }
    }
}