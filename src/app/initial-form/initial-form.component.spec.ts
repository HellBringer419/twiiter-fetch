import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InitialFormComponent } from './initial-form.component';

describe('InitialHeroesComponent', () => {
  let component: InitialFormComponent;
  let fixture: ComponentFixture<InitialFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InitialFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InitialFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
