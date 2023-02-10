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

export default configureStore({
  reducer: { 
    user : user.reducer,
    token : token.reducer
  }
})