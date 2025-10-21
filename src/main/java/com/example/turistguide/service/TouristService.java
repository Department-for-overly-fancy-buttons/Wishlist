package com.example.turistguide.service;

import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) {
        if (!touristAttraction.getName().isEmpty()
                && touristRepository.getTouristAttraction(touristAttraction.getName()) == null) {
            return touristRepository.addTouristAttraction(touristAttraction);
        }
        return null;
    }

    public List<TouristAttraction> getAllTouristAttraction() {
        return touristRepository.getAllTouristAttraction();
    }

    public TouristAttraction getTouristAttraction(String name) {
        return touristRepository.getTouristAttraction(name);
    }

    public void updateTouristAttraction(TouristAttraction touristAttraction) {
        touristRepository.updateTouristAttraction(touristAttraction);
    }

    public void deleteTouristAttraction(String name) {
        touristRepository.deleteTouristAttraction(name);
    }

    public List<String> getCities() {
        return touristRepository.getCities();
    }

    public List<String> getTags() {
        return touristRepository.getTags();
    }
}
