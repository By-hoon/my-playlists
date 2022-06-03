import { useLocation } from "react-router-dom";
import PlayBox from "../components/PlayBox";
import Playlist from "../components/Playlist";
import PlaylistCategory from "../components/PlaylistCategory";

interface StateProps {
  page: string;
}

const Home = () => {
  const { page } = (useLocation().state as StateProps) || {
    page: "myPlaylist",
  };
  return (
    <PlayBox
      left={
        page === "allPlaylist" ? (
          <> {/*모든 플레이리스트 */}</>
        ) : page === "bookmarks" ? (
          <>{/*즐겨찾기한 플레이리스트 */}</>
        ) : (
          <>
            <Playlist page={page} />
          </>
        )
      }
      right={<PlaylistCategory page={page} />}
    />
  );
};

export default Home;
