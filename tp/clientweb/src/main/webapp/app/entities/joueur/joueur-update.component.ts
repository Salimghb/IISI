import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IJoueur } from 'app/shared/model/joueur.model';
import { JoueurService } from './joueur.service';

@Component({
    selector: 'jhi-joueur-update',
    templateUrl: './joueur-update.component.html'
})
export class JoueurUpdateComponent implements OnInit {
    joueur: IJoueur;
    isSaving: boolean;

    constructor(protected joueurService: JoueurService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ joueur }) => {
            this.joueur = joueur;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.joueur.id !== undefined) {
            this.subscribeToSaveResponse(this.joueurService.update(this.joueur));
        } else {
            this.subscribeToSaveResponse(this.joueurService.create(this.joueur));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IJoueur>>) {
        result.subscribe((res: HttpResponse<IJoueur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
