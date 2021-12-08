package ch.ffhs.drugstore.domain.usecase.management.signatures;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import ch.ffhs.drugstore.data.dto.SignatureDto;
import ch.ffhs.drugstore.data.entity.Signature;
import ch.ffhs.drugstore.domain.service.SignatureService;
import ch.ffhs.drugstore.domain.usecase.UseCase;

public class GetSignatures implements UseCase<LiveData<List<SignatureDto>>, Void> {
  @Inject SignatureService signatureService;

  @Inject
  public GetSignatures(SignatureService signatureService) {
    this.signatureService = signatureService;
  }

  @Override
  public LiveData<List<SignatureDto>> execute(Void unused) {
    return signatureService.getSignatures();
  }
}
