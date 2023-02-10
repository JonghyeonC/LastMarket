import {Cookies} from 'react-cookie'

const cookies = new Cookies()

export const setCookie = ()=>{
	return cookies.set(name, value, {...options})
}

export const getCookie = ()=>{
	return cookies.get(name)
}