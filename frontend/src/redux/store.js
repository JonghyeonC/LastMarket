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

export default configureStore({
  reducer: { 
    user : user.reducer
  }
})