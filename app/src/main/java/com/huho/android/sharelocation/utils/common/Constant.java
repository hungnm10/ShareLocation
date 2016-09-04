package com.huho.android.sharelocation.utils.common;

import android.location.LocationManager;

public class Constant {

	public static float meterToMile = (float) 0.00062137119;
	public static String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;

	public static String PREFS_NAME = "MyPrefsFile";
	public static String IS_FIRST_TIME_USE = "is_my_first_time";
	public static String kUserId = "user_id";
	public static String kSessionId = "session_id";
	public static String kOrderOnline = "order_online";

	public static String COUPON_TYPE_PERCENT = "percent";
	public static String COUPON_TYPE_AMOUNT = "amount";

	public static String ORDER_TYPE_PICK_UP = "pickup";
	public static String ORDER_TYPE_DELIVERY = "delivery";

	public static String kTagHomeFragment = "HOME_FRAGMENT";
	public static String kTagCheckOutFragment = "CHECKOUT_FRAGMENT";
	public static String kTagSearchNewFragment = "SEARCH_NEW_ADDRESS";

	public static String kMessageTitleGetDeliveryAddr = "Get Delivery addresses";
	public static String kMessageTitleGetPhoneNum = "Get Phone numbers";
	public static String kMessageTitleGetCreditCard = "Get Credit cards";

	public static String kMessageTitleCouponNotStart = "Coupon date not started yet";
	public static String kMessageTitleReorderSuccess = "Re-order";
	public static String kMessageCartEmpty = "Your cart is empty. Add some food from your favorite restaurant";
	public static String kMessageTitleClearCart = "Clear your cart?";
	public static String kMessageTitleStampCardSuccess = "Stamp Card Success";
	public static String kMessageTitleStampCardFailure = "Stamp Card Failure";
	public static String kMessageTitleStampCardLocation = "Stamp Card - location services";
	public static String kMessageUserPassNotMatch = "Username and Password does not match";
	public static String kMessageNeedLogin = "Oops... Please sign in to access your GrubBook";
	public static String kMessageMenuNA = "Restaurant menu not available";
	public static String kMessageHourNA = "Restaurant hours not available";
	public static String kMessagePhoneNA = "Restaurant phone number not available";
	public static String kMessageDicrectionNA = "Directions not available";
	public static String kMessageSelectPayment = "Please select a payment method";
	public static String kMessageCouponStarting = "Coupon is valid starting";
	public static String kMessageReorderSuccess = "The order has been added to your cart";
	public static String kMessageClearCart = "Do you want to clear your cart and start a new order with this restaurant?";
	public static String kMessagQuickCheckCart = "Would you like to pick up or have your food delivered?";

	public static String kMessageStampCodeInvalid = "Invalid Stamp Code";
	public static String kMessageNeedLogin2 = "Please sign in your GrubBook account";
	public static String kMessageCouponDisabled = "Coupons is temporarily disabled";
	public static String kMessageStampcardDisabled = "Stamp Card is temporarily disabled";
	public static String kMessageTurnOnLocation = "Please turn on location services for direction.";
	public static String kMessageInputValidZipcode = "Please input valid ZipCode";
	public static String kMessageTurnOnLocationSearch = "We're having trouble determing your GPS location. Please check your location settings and try again.";
	public static String kMessageNoResult = "Your search returns no results.";
	public static String kMessageEnterSearchAddress = "Please enter address";
	public static String kMessagePassNotMatch = "Password does not match";
	public static String kMessageEnterYourName = "Please enter your first name and last name";
	public static String kMessagePassLengthNotMatch = "Password must be minimum 8 characters";
	public static String kMessageSignOuConfirm = "You won't be able to access your GrubBook if you sign out";
	public static String kMessageAddToGrubSuccess = "This restaurant has been added to your GrubBook.";
	public static String kMessageAddressNotValid = "We weren't able to locate this address. Please verify the address and try again";
	public static String SPINNER_ID_NON = "spinner_id_non";
	public static String SELECT_AN_ADDRESS = "Select an Address";
	public static String PLEASE_SELECT_AN_ADDRESS = "Please select an Address";
	public static String ENTER_PHONE_NUMBER = "Please enter a phone number";
	public static String SELECT_PHONE_NUMBER = "Select a Phone number";
	public static String PLEASE_SELECT_PHONE_NUMBER = "Please select a Phone number";
	public static String SELECT_CREDIT_CARD = "Select a Credit card";
	public static String PLEASE_SELECT_CREDIT_CARD = "Please select a Credit card";
	public static String SEARCH_NEW_ADDRESS = "Search New Address";
	public static String ADD_NEW_ADDRESS = "Add New Address";
	public static String ENTER_NEW_ADDRESS = "Enter new address";
	public static String ADD_NEW_PHONE = "Add New Phone Number";
	public static String ADD_NEW_CREDIT_CARD = "Add New Credit Card";

	public static enum TIP_TYPE {
		TIP_NONE, TIP_10, TIP_15, TIP_20, TIP_CASH
	}

	public static enum FLAG {
		REWARD, COUPON
	}
	public static enum FLAG_SORT_REWARD{
		name,recent,aivailable,expired
	}
	public static enum FLAG_SORT_COUPON{
		name,recent,expired
	}
	public static enum PAYMENT_METHOD {
		PAYMENT_TYPE_NONE, PAYMENT_TYPE_CREDIT, PAYMENT_TYPE_PAYPAL, PAYMENT_TYPE_CASH
	}

	public static enum SORT_TYPE {
		SORT_TYPE_DISTANCE, SORT_TYPE_POPULAR, SORT_TYPE_MINIMUM, SORT_TYPE_AZ
	}

	public static String DINE_IN = "Dine-in";
	public static String PICK_UP = "Pick Up";
	public static String DELIVERY = "Delivery";
	public static String INTERNET_CONNECT_ERROR = "No internet connection available. Please check your internet settings";
	/*
	 * AUTH
	 */
	public static String SIGN_UP_URL = "auth/signup";
	public static String FORGOT_PASS_URL = "auth/forgot_password";
	public static String LOGIN_URL = "auth/login";
	public static String LOGOUT_URL = "auth/logout";
	/*
	 * MY PROFILE
	 */
	public static String GET_PROFILE_URL = "myprofile/get_profile";
	public static String EDIT_PROFILE_URL = "myprofile/edit_profile";
	public static String CHANGE_PASSWORD = "myprofile/change_password";
	public static String GET_MY_ADDRESS = "myprofile/get_addresses";
	public static String ADD_ADDRESS = "myprofile/add_address";
	public static String EDIT_ADDRESS = "myprofile/edit_address";
	public static String DELETE_ADDRESS = "myprofile/delete_address";
	public static String PUT_LOCATION = "myprofile/put_location";
	public static String GET_MY_PHONE_NUM = "myprofile/get_phones";
	public static String ADD_PHONE_NUM = "myprofile/add_phone";
	public static String EDIT_PHONE_NUM = "myprofile/edit_phone";
	public static String DELETE_PHONE_NUM = "myprofile/delete_phone";
	public static String ADD_COMMENT = "myprofile/add_comment";
	/*
	 * Restaurants
	 */
	public static String GET_SEARCH_ADDRESS = "restaurants/get_search_address";
	public static String SEARCH_BY_ADDRESS = "restaurants/search_by_address";
	public static String SEARCH_BY_ADDRESS_ID = "restaurants/search_by_address_id";
	public static String SEARCH_NON_LOGIN = "restaurants/search_non_login";
	public static String GET_RESTAURANT_INFO_NON_LOGIN = "restaurants/get_restaurant_info_non_login";
	public static String GET_LOCU_INFO_NON_LOGIN = "restaurants/get_locu_info_non_login";
	public static String SEARCH = "restaurants/search";
	public static String SET_FAVORITE = "restaurants/set_favourite";
	public static String UN_FAVORITE = "restaurants/unset_favourite";
	public static String FAVORITE_GET_LIST = "restaurants/favourites";
	public static String GET_REWARDS = "rewards/get_rewards";
	public static String GET_REWARDS_BY_REST_ID = "rewards/get_rewards_by_rest_id";
	public static String REDEEM_REQUEST_REST = "rewards/redeem_request";
	public static String CANCEL_REDEEM_REQUEST_REST = "rewards/cancel_redeem_request";
	public static String PRECHECK_REDEEM_REQUEST_REST = "rewards/precheck_redeem_request";
	public static String REDEEM_REQUEST_ONLINE = "rewards/redeem_online";
	public static String REDEEM_REQUEST_CANCEL_ONLINE = "rewards/cancel_redeem_online";
	public static String GET_RESTAURANT_INFO = "restaurants/get_restaurant_info";

	public static String GET_LOCU_INFO = "restaurants/get_locu_info";
	public static String GET_COUPON_BY_RES_ID = "restaurants/get_coupon_list";
	public static String WRITE_REVIEW = "restaurants/write_review";
	public static String GET_REVIEW = "restaurants/get_reviews";
	public static String SCAN_RES_QRCODE = "restaurants/scan_qrcode";
	public static String SCAN_RES_QRCODE_HOME = "rewards/scan_qr_homepage";
	public static String SCAN_RES_QRCODE_REWARDS = "rewards/scan_qr";
	public static String SCAN_RESTAURANT_QRCODE_NONLOGIN = "restaurants/scan_qrcode_non_login_reward";
	public static String IS_FAVORITE = "restaurants/is_favourite";
	public static String IS_RANGE = "orders/validate_address";

	/*
	 * Optimazation
	 */
	public static String GET_RESTAURANT_INFO_OPT = "optimizations/get_restaurant_info";
	public static String SEARCH_HASHCODE = "optimizations/search_hashcode";
	public static String FAVORITE_GET_LIST_OPT = "optimizations/favourites_list";


	/*
	 * Orders
	 */
	public static String ADD_CART = "orders/add_cart";
	public static String GET_MY_CART = "orders/get_cart";
	public static String GET_ITEM = "orders/get_item";
	public static String EDIT_ITEM = "orders/edit_item";
	public static String DELETE_ORDER = "orders/delete";
	public static String PLACE_ORDER = "orders/place";
	public static String ORDER_CONFIRM = "orders/confirm";
	public static String ORDER_CONFIRM_WITH_PAYPAL = "orders/confirm_paypal";
	public static String GET_CURRENT_ORDER = "orders/get_current";
	public static String GET_PAST_ORDER = "orders/get_past";
	public static String RE_ORDER = "orders/reorder";
	public static String APPLY_COUPON = "orders/apply_coupon";
	public static String GET_ORDER_STATUS = "orders/get_order_status";

	/*
	 * Paypal
	 */
	public static String VERIFY_PAYPAL = "payments/paypal_verify";
	public static String GET_PAYPAL_APPID = "payments/get_paypal_app_id";
	/*
	 * Spreedly
	 */
	public static String GET_CC_LIST = "payments/get_cc_list";
	public static String DELETE_CC = "payments/delete_cc";
	public static String REGIS_CC = "payments/regist_new_cc";
	public static String PAY_WITH_CC = "payments/pay_with_cc";
	/*
	 * Coupons
	 */
	public static String GET_MY_COUPON = "coupons/get_mycoupons";
	public static String ADD_MY_COUPON = "coupons/add_mycoupon";
	public static String DELETE_MY_COUPON = "coupons/delete_mycoupon";

	/*
	 * Stampcard
	 */
	public static String GET_STAMPCARD_HISTORY = "stampcards/get_histories";
	public static String SCAN_STAMPCARD_QRCODE = "stampcards/scan_qrcode";
	public static String ADD_STAMP = "stampcards/add_stamp";
	public static String REDEEM = "stampcards/redeem";
	/*
	 * UserSetting
	 */
	public static String FLAG_EDIT_NAME = "editname";
	public static String FLAG_EDIT_EMAIL = "editemail";
	public static String FLAG_EDIT_PASS = "editpass";
	/*
	 * Delivery Address
	 */
	public static String FLAG_EDIT_MYADDRESS = "edit";
	public static String FLAG_NEW_MYADDRESS = "newadd";


	/*
	 * Delivery Message
	 */
	public static String dSelectAnAddress = "Please select an address!";
}

