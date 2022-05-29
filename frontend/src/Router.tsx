import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Add from "./routes/add";
import Home from "./routes/home";
import Login from "./routes/login";
import Playlist from "./routes/playlist";

const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Home />} />
        <Route path="/playlist" element={<Playlist />} />
        <Route path="/playlist/add" element={<Add />} />
        <Route path="*" element={<Navigate replace to="/" />} />
      </Routes>
    </BrowserRouter>
  );
};
export default Router;
