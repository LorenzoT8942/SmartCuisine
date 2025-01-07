import logo from './logo.svg';
import './App.css';
import './CSS/home-shopping-list.css'
import {useEffect} from "react";
import UserProfileComp from './components/UserProfile.tsx'
import { BrowserRouter as Router, Route, Routes  } from 'react-router-dom';
import { useNavigate } from "react-router-dom";
import Login from './components/Login.tsx';
import SignUp from './components/SignUp.tsx';
import SearchBar from './components/SearchBar.tsx'
import SearchRecipeResults from './components/SearchRecipesResults.tsx'
import RecipeInfoPage from './components/RecipeInfoPage/RecipeInfoPage.tsx'
import ShoppingListPage from './components/ShoppingList/ShoppingListPage.tsx'
import ShoppingListDetail from './components/ShoppingList/ShoppingListDetail.tsx';
function App() {

    useEffect(() => {
        // Set background color when the component mounts
        document.body.style.backgroundColor = '#88c265';
        // If you want an image as background:
        // document.body.style.backgroundImage = "url('path/to/your-image.jpg')";
        // document.body.style.backgroundSize = 'cover';
        // document.body.style.backgroundPosition = 'center center';
        // document.body.style.backgroundRepeat = 'no-repeat';
    }, []); // Empty dependency array means this will run only once when the component mounts

    return (
        <Router>
          <Routes>
            {/* Route for HomePage */}
            <Route path="/" element={<HomePage />} />

            {/* Routes for recipes search */}
            <Route path="/results" element={<SearchRecipeResults />} />
            {/* Route for RecipeInfoPage */}
            <Route path="/recipes/info/:recipeId" element={<RecipeInfoPage />} />
            {/* Route for ShoppingList */}
            <Route path="/shopping-list" element={<ShoppingListPage />} />
            <Route path="/shopping-list/:listId" element={<ShoppingListPage />} />
            {/* Route for Storage */}
            {/* Route for UserProfileWithNotifications */}
            <Route path="/profile" element={<UserProfileComp />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/login" element={<Login />} />
            <Route render={() => <h1>Page not found</h1>} />
          </Routes>
        </Router>
      );
}

function HomePage() {

    const isLoggedIn = localStorage.getItem('authToken') != null;
    let loginButton;
    let logoutButton;
    let signUpButton;
    let profileButton;

    const navigate = useNavigate();

    const handleSearch = (query) => {
        navigate(`/results?query=${encodeURIComponent(query)}`);
    }

    const handleProfile = () => {

        window.location.href = '/profile';

    };
    const handleSignUp = () => {

        window.location.href = '/signup';

    };

    const handleLogin = () => {

        window.location.href = '/login';

    };

    const handleLogout = () => {
        localStorage.removeItem('authToken'); // Clear the auth token from localStorage
        alert('Logged out successfully!');
        window.location.reload();
      };

    if (isLoggedIn) {
        logoutButton = (
            <button onClick={handleLogout} style={{ marginLeft: '10px' }}>
                Logout
            </button>
        );
        profileButton = (
            <button onClick={handleProfile} style={{ marginLeft: '10px' }}>
                Profile
            </button>
        );
    } else {
        loginButton = (
            <button onClick={handleLogin} style={{ marginLeft: '10px' }}>
                Login
            </button>
        );
        signUpButton = (
            <button onClick={handleSignUp} style={{ marginLeft: '10px' }}>
                Signup
            </button>
        );
    }

    const containerStyles = {
        margin: '0 auto',
        marginTop: '50px',
        maxWidth: '1000px', // Limits the max width for larger screens

        borderRadius: '8px',
    };

    

    return (
    
        // <div style={containerStyles}>
        //     <SearchBar onSearch={handleSearch} />
        //     <div style={{ marginTop: '20px' }}>
        //         {loginButton}
        //         {logoutButton}
        //         {signUpButton}
        //         {profileButton}
        //     </div>
        // </div>
    <div className="app-container">
      <h1 className="app-title">Smart Cuisine</h1>  
      <SearchBar onSearch={handleSearch} />
      <div className="button-container">
        <button className="nav-button" onClick={() => navigate('/storage')}>Storage</button>
        <button className="nav-button" onClick={() => navigate('/shopping-list')}>Shopping List</button>
        <button className="nav-button" onClick={() => navigate('/profile')}>Profile</button>
        {isLoggedIn ? (
            <button className="logout-button" onClick={handleLogout}>Logout</button>
          ) : (
            <button className="nav-button" onClick={handleLogin}>Login</button>
          )}
      </div>
    </div>
    );
}
export default App;

