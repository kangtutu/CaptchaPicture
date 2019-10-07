## 构造方法

1. 无参构造方法

```
GenerateImage()
初始化新创建的 GenerateImage 对象。
```

2. 有参构造方法

```
GenerateImage(
	img_width,
	img_height,
	img_code_random_num,
	color_red,
	color_green,
	color_blue,
	img_code_length
)
构建一个自定义参数的 GenerateImage 对象
```

| 参数                     | 说明                                                         |
| ------------------------ | ------------------------------------------------------------ |
| img_width                | 验证码图片宽度；默认值 80                                    |
| img_height               | 验证码图片高度；默认值 35                                    |
| verification_code_range  | 验证码随机取值范围；3 -- 默认值，代表不限制取值范围；0 -- 在大写字母范围内生成验证码；1 -- 只在小写字母范围内生成验证码；2 -- 在数字范围内生成验证码 |
| color_red                | 验证码图片背景颜色及字体的红色取值范围：0~255 范围；默认值 255 |
| color_green              | 验证码图片背景颜色及字体的绿色取值范围：0~255 范围；默认值 255 |
| color_blue               | 验证码图片背景颜色及字体的蓝色取值范围：0~255 范围；默认值 255 |
| verification_code_length | 验证码生成的长度；默认长度：4                                |



## 生成验证码及图片的方法

```
Map<String, Object>  getImage()
通过此方法可以获取生成的验证码的值及生成的验证码图片的byte数组。
```

| key值 | value值              |
| ----- | -------------------- |
| code  | 获取生成的验证码     |
| image | 获取生成的验证码图片 |



## 测试案例

```java
/**
 * 【 produces = "image/jpg" 】此属性表示指定返回的数据格式
 */
@RequestMapping(value = "/img",method = RequestMethod.GET,produces = "image/jpg")
    public byte[] image(){
        CaptchaPicture generateImage = new CaptchaPicture(80,35,3,255,255,255,4);
        Map<String, Object> image = generateImage.getImage();
        return (byte[])image.get("image");
}
```

