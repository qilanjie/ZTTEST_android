package com.zh.HQL.fragment;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;

import com.zh.HQL.activity.Order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtil {
	//内存地址
	public static String root = Environment.getExternalStorageDirectory()
			.getPath();

	public static void writeExcel(Context context, List<Order> exportOrder,
			String fileName) throws Exception {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)&&getAvailableStorage()>1000000) {
			Toast.makeText(context, "SD卡不可用", Toast.LENGTH_LONG).show();
			return;
		}
		String[] title = { "ID", "名称", "一次", "二次", "平均", "        测试时间        " };
		File file;
		File dir = new File(context.getExternalFilesDir(null).getPath());
		file = new File(dir, fileName + ".xls");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 创建Excel工作表
		WritableWorkbook wwb;
		OutputStream os = new FileOutputStream(file);
		wwb = Workbook.createWorkbook(os);
		// 添加第一个工作表并设置第一个Sheet的名字
		WritableSheet sheet = wwb.createSheet("历史记录", 0);

		sheet.setColumnView( 0 , 16 );
		sheet.setColumnView( 1 , 16 );
		sheet.setColumnView( 2 , 12 );
		sheet.setColumnView( 3 , 22 );
		sheet.setColumnView( 4 , 22 );
        sheet.setColumnView( 5 , 46 );
		sheet.setRowView(0, 600, false); //设置行高
		Label label;
		for (int i = 0; i < title.length; i++) {
			// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
						// 在Label对象的子对象中指明单元格的位置和内容
			label = new Label(i, 0, title[i], getHeader());

			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);
		}

		for (int i = 0; i < exportOrder.size(); i++) {
			Order order = exportOrder.get(i);
			sheet.setRowView(i+1, 500,false); //设置行高
			Label id = new Label(0, i + 1, order.id,getContent());
			Label name = new Label(1, i + 1, order.name,getContent());
			Label hanqilang1 = new Label(2,i+1,order.hanqilang1,getContent());
			Label hanqiliang2 = new Label(3, i + 1, order.hanqiliang2,getContent());
			Label hanqiliangpj = new Label(4, i + 1, order.hanqiliangpj,getContent());
			Label ceShiShiJian = new Label(5, i + 1, order.ceShiShiJian,getContent());

			sheet.addCell(id);
			sheet.addCell(name);
			sheet.addCell(hanqilang1);
			sheet.addCell(hanqiliang2);
            sheet.addCell(hanqiliangpj);
			sheet.addCell(ceShiShiJian);


			
		}
		// 写入数据
		wwb.write();
		// 关闭文件
		wwb.close();
		Toast.makeText(context, "写入内部文件", Toast.LENGTH_SHORT).show();
	}

	public static WritableCellFormat getHeader() {
		WritableFont font = new WritableFont(WritableFont.TAHOMA, 16,
				WritableFont.BOLD);// 定义字体
		try {
			font.setColour(Colour.BLUE);// 蓝色字体
		} catch (WriteException e1) {
			e1.printStackTrace();
		}
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中

			// format.setBorder(Border.ALL, BorderLineStyle.THIN,
			// Colour.BLACK);// 黑色边框
			// format.setBackground(Colour.YELLOW);// 黄色背景
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;
	}
    public static WritableCellFormat getContent() {
        WritableFont font = new WritableFont(WritableFont.TAHOMA, 14,
                WritableFont.NO_BOLD);// 定义字体
        WritableCellFormat format = new WritableCellFormat(font);
        try {
            format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中

            // format.setBorder(Border.ALL, BorderLineStyle.THIN,
            // Colour.BLACK);// 黑色边框
//             format.setBackground(Colour.LIGHT_BLUE);// 黄色背景
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return format;
    }
	/** 获取SD可用容量 */
	private static long getAvailableStorage() {

		StatFs statFs = new StatFs(root);
		long blockSize = statFs.getBlockSize();
		long availableBlocks = statFs.getAvailableBlocks();
		long availableSize = blockSize * availableBlocks;
		// Formatter.formatFileSize(context, availableSize);
		return availableSize;
	}
}
