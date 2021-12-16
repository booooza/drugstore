package ch.ffhs.drugstore.presentation.dispensary.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import ch.ffhs.drugstore.domain.usecase.dispensary.AddToFavorites;
import ch.ffhs.drugstore.domain.usecase.dispensary.DispenseDrug;
import ch.ffhs.drugstore.domain.usecase.dispensary.GetAllDispensaryItems;
import ch.ffhs.drugstore.presentation.dispensary.view.DispensaryFilters;
import ch.ffhs.drugstore.presentation.dispensary.view.FilterState;
import ch.ffhs.drugstore.shared.dto.dispensary.SubmitDispenseDto;
import ch.ffhs.drugstore.shared.dto.management.drugs.DrugDto;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DispensaryViewModel extends AndroidViewModel {
    private final LiveData<List<DrugDto>> items;
    private final MutableLiveData<FilterState<DispensaryFilters>> filterState =
            new MutableLiveData<>();
    @Inject
    GetAllDispensaryItems getAllDispensaryItems;
    @Inject
    AddToFavorites addToFavorites;
    @Inject
    DispenseDrug dispenseDrug;

    @Inject
    public DispensaryViewModel(Application application) {
        super(application);
        filterState.setValue(new FilterState<>());
        items = Transformations.switchMap(filterState, f
                -> getAllDispensaryItems.execute(Objects.requireNonNull(filterState.getValue())));
    }

    public LiveData<List<DrugDto>> getItems() {
        return items;
    }

    public LiveData<FilterState<DispensaryFilters>> getFilterState() {
        return filterState;
    }

    public void filter(FilterState<DispensaryFilters> filters) {
        filterState.postValue(filters);
    }

    public void addToFavorites() {
        addToFavorites.execute(null);
    }

    public void dispenseDrug(int drugId, String employee, String patient, String sDosage)
            throws Exception {
        double dosage = Double.parseDouble(sDosage);
        SubmitDispenseDto submitDispenseDto = new SubmitDispenseDto(drugId, employee, patient,
                dosage);
        dispenseDrug.execute(submitDispenseDto);
    }
}