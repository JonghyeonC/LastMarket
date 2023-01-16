const REST_API_KEY = "5d6f9b9973706d6389a07d2bcf07e40b";
const REDIRECT_URI =  "localhost:3000";

export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;