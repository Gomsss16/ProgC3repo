import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuArchivo } from './menu-archivo';

describe('MenuArchivo', () => {
  let component: MenuArchivo;
  let fixture: ComponentFixture<MenuArchivo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MenuArchivo]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuArchivo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
