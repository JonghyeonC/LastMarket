import { configureStore, createSlice } from '@reduxjs/toolkit'

let user = createSlice({
  name : 'user',
  initialState : null,
  reducers : {
    addUserInfo(state, a) {
      return a.payload
    }
  }
})

export const { addUserInfo } = user.actions

let token = createSlice({
  name : 'token',
  initialState : null,
  reducers : {
    addToken(state, b) {
      return b.payload
    }
  }
})

export const { addToken } = token.actions

let userInfo = createSlice({
  name : 'userInfo',
  initialState : null,
  reducers : {
    addInfo(state, c) {
      return c.payload
    }
  }
})

export const { addInfo } = userInfo.actions

export default configureStore({
  reducer: { 
    user : user.reducer,
    token : token.reducer,
    userInfo : userInfo.reducer
  }
})