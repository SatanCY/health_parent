一个健康管理系统，学习用

**9/4:添加了套餐添加功能，实现了图片上传服务器以及预览**

**9/5:完善了套餐功能模块，实现了分页查询、编辑删除等功能，并使用redis来缓存图片，解决服务器上产生的冗余的图片垃圾，修复了删除检查组时未能成功的问题**

**9/7:在根据日期查询预约设置时，由于某些月份没有完整的31天，使用“-31”作为条件查询时，数据库无法查出相应数据，所以使用LAST_DAY(date)获取当月最后一一天**

bug:往数据库插入date数据时，出现插入的数据为前一天，导致按日期数据进行的操作全都失效，后发现是因为mysql驱动的时区设置与数据库服务器的时区不一致导致的

**9/8:实现了手机验证码以及体检预约功能**