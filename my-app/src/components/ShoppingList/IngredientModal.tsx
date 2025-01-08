import React, { useState } from 'react';
import axios from 'axios';
import '../../CSS/ingredient-modal.css';

interface Ingredient {
    id: number;
    name: String;
    quantity: number;
    unit: String;
}

interface IngredientModalProps {
    onClose: () => void;
    onAdd: (ingredient: Ingredient) => void;
}

const IngredientModal: React.FC<IngredientModalProps> = ({ onClose, onAdd }) => {
    const [searchTerm, setSearchTerm] = useState('');
    const [searchResults, setSearchResults] = useState<Ingredient[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const token = localStorage.getItem('authToken');

    const handleSearch = async (e: React.FormEvent) => {
        if(token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        e.preventDefault();
        setLoading(true);
        setError(null);
        try {
            const response = await axios.get(`http://localhost:3004/recipes-ingredients/ingredients/search-by-name?name=${searchTerm}`, {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`
                }
            });
            setSearchResults(response.data);
            console.log(response.data);
        } catch (err) {
            setError('Error fetching ingredients');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="close-button" onClick={onClose}>X</button>
                <h2>Search Ingredient</h2>
                <form onSubmit={handleSearch}>
                    <input
                        type="text"
                        className="search-bar"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        placeholder="Search for an ingredient"
                    />
                    <button type="submit">Search</button>
                </form>
                {loading && <p>Loading...</p>}
                {error && <p>{error}</p>}
                <ul>
                    {searchResults.length > 0 ? (
                        searchResults.map((ingredient) => (
                            <li key={ingredient.id} onClick={() => onAdd(ingredient)}>
                                {ingredient.name}
                            </li>
                        ))
                    ) : (
                        <p>No ingredients found</p>
                    )}
                </ul>
            </div>
        </div>
    );
};

export default IngredientModal;