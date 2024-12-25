// utils/storage.js
import CryptoJS from 'crypto-js';

const SECRET_KEY = 'your-secret-key'; // 确保使用安全的密钥

// 加密数据
export const set = (key, value) => {
  try {
    const encryptedValue = CryptoJS.AES.encrypt(JSON.stringify(value), SECRET_KEY).toString();
    localStorage.setItem(key, encryptedValue);
  } catch (error) {
    console.error('加密失败:', error);
  }
};

// 解密数据
export const get = (key) => {
  try {
    const encryptedValue = localStorage.getItem(key);
    if (!encryptedValue) return null;

    const bytes = CryptoJS.AES.decrypt(encryptedValue, SECRET_KEY);
    const decryptedValue = bytes.toString(CryptoJS.enc.Utf8);
    
    // 确保解密后的值是有效的 JSON
    return JSON.parse(decryptedValue);
  } catch (error) {
    console.error('解密失败:', error);
    return null; // 返回 null 或者其他默认值
  }
};

export const del = (key) => {
  localStorage.removeItem(key)
}

export const setToken = (value) => {
  set('token', value)
}

export const getToken = () => {
  return get('token')
}

export const removeToken = () => {
  del('token')
  del('userInfo')
}

export const setUser = (value) => {
  set('userInfo', value)
}
export const getUser = () => {
  return get('userInfo')
}