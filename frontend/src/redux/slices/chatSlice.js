import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const initialState = {
  messages: [],
  chatList: [],
  isLoading: true,
};

export const getChatMessage = createAsyncThunk(
  "GET_CHAT_MESSAGE",
  async (payload, thunkAPI) => {
    const config = {
      headers: {
        "X-ACCESS-TOKEN": window.localStorage.getItem("token"),
      },
    };
    try {
      const response = await axios.get(
        `/api/chat-rooms/${payload.roomId}/messages`,
        config
      );
      return response;
    } catch (e) {
      return thunkAPI.rejectWithValue(e.response.data);
    }
  }
);

const chatSlice = createSlice({
  name: "chat",
  initialState,
  reducers: {
    subMessage(state, action) {
      state.messages.data.push(action.payload);
    },
  },
  extraReducers: (builder) => {
    builder.addCase(getChatMessage.fulfilled, (state, action) => {
      state.messages = action.payload.data;
    });
  },
});

export const { subMessage } = chatSlice.actions;
export default chatSlice.reducer;
