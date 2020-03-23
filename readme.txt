更新记录

框架地址 
https://github.com/LzDocker/NitSample.git
分支：pro_asmart

整体依赖关系 图一

![](http://doc.wgc360.com/server/../Public/Uploads/2020-03-23/5e7887eed8e6b.png)

整体关系
![](http://doc.wgc360.com/server/../Public/Uploads/2020-03-23/5e78880f4420c.png)

主要更新

1 provider 文件夹

1）应用间的依赖严格按照图一中的依赖关系来建立，整体规则为新增应用只引用circiev2 .
2）app module 只引用所需要的应用，而不会引用底层库
3）应用间需要跳转使用arouter
4) 跨应用调用方法时使用 arouter的服务即provider 实现步骤：
    a) 
	![](http://doc.wgc360.com/server/../Public/Uploads/2020-03-23/5e788a52b691a.png)

b) 在对应应用provider 目录下实现接口 示例如下：
 ![](http://doc.wgc360.com/server/../Public/Uploads/2020-03-23/5e788a114f927.png)

2 应用列表和动态列表数据兼容  核心是为了处理应用间解藕，同时提高列表的开发速度，把之前需要开发两次的列表用一个列表兼容 。目的：以后再遇到新的应用开发需使用此模式开发。实现原理：retorfit 添加converter自定义json解析规则  
场景如下：活动应用，在应用内请求列表的时候 返回数据结构为 list<ActiveVo> ， 
                在动态列表中接口返回数据结构为 List<DynamicData<ActiveVo>  按照之前的实现方式无法统一列表
解决步骤：a) ActiveVo 继承自 ExtDataBase
 ![](http://doc.wgc360.com/server/../Public/Uploads/2020-03-23/5e788cb5e087d.png)
              b）circlev2->util->paser
			  ![](http://doc.wgc360.com/server/../Public/Uploads/2020-03-23/5e788d161dfb2.png)
			 在createGson方法中新增你的实现 type 和 解析类的全路径名即可
             

