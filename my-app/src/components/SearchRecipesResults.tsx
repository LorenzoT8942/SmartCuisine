import React from "react";
import RecipeList from "./RecipeList";
import SearchBar from "./SearchBar";

const ResultsPage = () => {
    return (
        <SearchBar onSearch=
        )
    };



const ResultsContainer = ({ recipes }) => {
  return (
    <div>
      <h1>Search Results</h1>
      {recipes.length > 0 ? (
        <RecipeList recipes={recipes} />
      ) : (
        <p>No recipes found. Try another search!</p>
      )}
    </div>
  );
};

export default ResultsPage;
