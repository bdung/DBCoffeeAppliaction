package com.example.dbcoffeeapplication.Utils;


import com.example.dbcoffeeapplication.DTO.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utils {

    // Order
//    public static ArrayList<Product> _productList = new ArrayList<>(); // sản phẩm chọn mua
//    public static ReceiverInfo _receiverInfo = new ReceiverInfo(); // địa chỉ mua hàng (để mặc định cho đến khi khách hàng thay đổi)
    public static String _newAddressID; // id dể tạo địa chỉ mới
//    public static ArrayList<Voucher> _voucherList = new ArrayList<>(); // voucher được áp dụng cho đơn hàng
//    public static Product _product_current = new Product();
//    public static Product _new_product;
//    public static HashMap<String, Integer> _vouchersOfUser = new HashMap<>();
    public static Users _user = new Users();

    public static List<Users> _list_user = new ArrayList<>();
    public static List<Users> _admin_list_user = new ArrayList<>();


}
