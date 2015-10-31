package cn.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
/**
 * 生成验证码图片
 * @author Administrator
 * 更新时间：2015/1/1
 */
public class VerfiyCode {
	//图片的宽高
	private int width = 70;
	private int height = 35;
	private Random r = new Random();
	//可选的字体名称
	private String[] fontNames ={"宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};
	//背景色
	private Color bgColor = new Color(255,255,255);
	//可选字符
	private String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
	//验证码的文本
	private String text;
	//随机字体
	private Font randomFont(){
		int index = r.nextInt(fontNames.length);
		String fontName = fontNames[index];
		int style = r.nextInt(4);//字体样式： 0(无样式), 1(粗体), 2(斜体), 3(粗体+斜体)
		int size = r.nextInt(5)+24;//字体大小  24-28
		return new Font(fontName,style,size);
	}
	//生成随机颜色
	private Color randomColor(){
		int red = r.nextInt(150);
		int blue = r.nextInt(150);
		int green = r.nextInt(150);
		return new Color(red,blue,green);
	}
	//画干扰线
	private void drawLine(BufferedImage image){
		int num =3;
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(Color.blue);//设置干扰线的颜色为蓝色
		for(int i = 0;i < num; i++){
			int x1 = r.nextInt(this.width);
			int y1 = r.nextInt(this.height);
			int x2 = r.nextInt(this.width);
			int y2 = r.nextInt(this.height);
			g.drawLine(x1, y1, x2, y2);
		}
	}
	//随机生成一个字符
	private char randomChar(){
		int index = r.nextInt(codes.length());
		return codes.charAt(index);
	}
	//创建BufferedImage
	private BufferedImage createImage(){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(this.bgColor);
		g.fillRect(0, 0, width, height);
		return image;
	}
	//获取验证码的图片
	public BufferedImage getImage(){
		BufferedImage image = this.createImage();
		Graphics2D g = (Graphics2D) image.getGraphics();
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < 4;i++){
			String s = String.valueOf(randomChar());
			sb.append(s);
			g.setColor(randomColor());
			g.setFont(randomFont());
			float x = i * 1.0F * width / 4;
			int y = height - 5;
			g.drawString(s,	x, y);
		}
		this.text = sb.toString();
		this.drawLine(image);//画上干扰线
		return image;
	}
	//返回验证码的字符
	public String getText(){
		return this.text;
	}
	// 保存图片到指定的输出流
	public static void output(BufferedImage image,OutputStream out) throws IOException{
		ImageIO.write(image,"JPEG", out);
	}
	
}
