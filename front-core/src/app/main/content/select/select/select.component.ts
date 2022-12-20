import {Component, Input, ElementRef, ViewEncapsulation, EventEmitter, Renderer2, forwardRef, Output} from '@angular/core';
import {coerceBooleanProperty} from '@angular/cdk/coercion';
import {Subject} from 'rxjs';
import {ControlValueAccessor, NgControl, NG_VALUE_ACCESSOR} from '@angular/forms';
import { MatFormFieldControl } from '@angular/material/form-field';

@Component({
  selector: 'power-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.scss'],
  providers: [
    {provide: MatFormFieldControl, useExisting: SelectComponent},
    {provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => SelectComponent), multi: true}
  ],
  host: {
    '[class.example-floating]': 'shouldLabelFloat',
    '[id]': 'id',
    '[attr.aria-describedby]': 'describedBy',
  },
  encapsulation: ViewEncapsulation.None
})
export class SelectComponent implements MatFormFieldControl<Array<any>>, ControlValueAccessor {
  static nextId = 0;

  stateChanges = new Subject<void>();
  id = `select-${SelectComponent.nextId++}`;
  focused = false;
  ngControl: NgControl;
  errorState = false;
  controlType = 'power-select';
  describedBy = '';

  @Input()
  employeeTask;

  @Input() 
  noTranslate:boolean=false;

  @Output() openedChanged: EventEmitter<any> = new EventEmitter<any>();

  get empty() {
    return this._value == null || this._value.length === 0;
  }

  get shouldLabelFloat() {
    return this.focused || !this.empty;
  }


  @Input()
  get placeholder(): string {
    return this._placeholder;
  }

  set placeholder(value: string) {
    this._placeholder = value;
    this.stateChanges.next();
  }

  private _placeholder: string;

  @Input()
  get required(): boolean {
    return this._required;
  }

  set required(value: boolean) {
    this._required = coerceBooleanProperty(value);
    this.stateChanges.next();
  }

  private _required = false;

  @Input()
  get disabled(): boolean {
    return this._disabled;
  }

  set disabled(value: boolean) {
    this._disabled = coerceBooleanProperty(value);
    this.stateChanges.next();
  }

  private _disabled = false;

  @Input()
  get value(): any {
    return this._value;
  }

  set value(value: any) {
    this._value = value;
    this.stateChanges.next();
  }

  _value: any;

  @Input()
  get items(): Array<any> {
    return this._items;
  }

  set items(items: Array<any>) {
    this._items = items;
    if (this.label)
      this.itemsChange.next();
    this.stateChanges.next();
  }

  _items: Array<any>;

  @Input()
  get label(): string {
    return this._label;
  }

  set label(label: string) {
    this._label = label;
    if (this.items)
      this.itemsChange.next();
  }

  _label: string;

  @Input()
  get separator(): string {
    return this._separator;
  }

  set separator(separator: string) {
    this._separator = separator;
  }

  _separator: string;

  @Input()
  get multiple(): boolean {
    return this._multiple;
  }

  set multiple(multiple: boolean) {
    this._multiple = coerceBooleanProperty(multiple);
  }

  _multiple: boolean;

  @Input()
  get selectionModeOff(): boolean {
    return this._selectionModeOff;
  }

  set selectionModeOff(selectionModeOff: boolean) {
    this._selectionModeOff = coerceBooleanProperty(selectionModeOff);
  }

  _selectionModeOff: boolean;

  @Input()
  get masterToggle(): boolean {
    return this._masterToggle;
  }

  set masterToggle(masterToggle: boolean) {
    this._masterToggle = coerceBooleanProperty(masterToggle);
  }

  _masterToggle: boolean;

  @Input()
  get search(): boolean {
    return this._search;
  }

  set search(search: boolean) {
    this._search = coerceBooleanProperty(search);
  }

  _search: boolean;

  @Input()
  get largePanel(): boolean {
    return this._largePanel;
  }

  set largePanel(largePanel: boolean) {
    this._largePanel = coerceBooleanProperty(largePanel);
  }

  _largePanel: boolean;

  @Input()
  get property(): string {
    return this._property;
  }

  set property(property: string) {
    this._property = property;
  }

  _property: string = 'id';

  @Input()
  get none(): string {
    return this._none;
  }

  set none(none: string) {
    this._none = none;
  }

  _none: string;

  @Input()
  get moreThanOneSelectedTrigger(): boolean {
    return this._moreThanOneSelectedTrigger;
  }

  set moreThanOneSelectedTrigger(moreThanOneSelectedTrigger: boolean) {
    this._moreThanOneSelectedTrigger = coerceBooleanProperty(moreThanOneSelectedTrigger);
  }

  _moreThanOneSelectedTrigger: boolean;

  @Output('selectionChange') _selectionChange: EventEmitter<void>;

  groupedItems: Object;
  filter: string = '';
  type: string = 'all';
  itemsChange: Subject<void>;

  onChangeCallback: Function = () => {
  };
  onTouched: Function = () => {
  };


  constructor(private renderer: Renderer2,
              private elRef: ElementRef<HTMLElement>) {
    this.itemsChange = new Subject<void>();
    this._selectionChange = new EventEmitter<void>();
    this.itemsChange.subscribe(() => {
      if (this.items && this.items.length != 0) {
        this._label.split(this.separator).forEach(label => {
          if (!this.items[0].hasOwnProperty(label)) {
            throw new Error('Property \'' + this._label + '\' does not exist in object of items');
          }
        });
      }
      this.makeGroupedItems();
    });
  }

  ngOnDestroy() {
    this.stateChanges.complete();
    this.itemsChange.complete();
    this._selectionChange.complete();
  }

  setDescribedByIds(ids: string[]) {
    this.describedBy = ids.join(' ');
  }

  onContainerClick(event: MouseEvent) {
    return;
  }

  openedChange(event: boolean) {
    this.focused = !this.focused;
    this.openedChanged.emit(event);
  }

  writeValue(value: any) {
    this.value = value;
  }

  registerOnChange(fn: Function) {
    if (fn)
      this.onChangeCallback = fn;
  }

  registerOnTouched(fn: Function) {
    if (fn)
      this.onTouched = fn;
  }

  setDisabledState?(disabled: boolean) {
    this.disabled = disabled;
    this.renderer.setProperty(this.elRef.nativeElement, 'disabled', this.disabled);
  }

  selectionChange() {
    this.errorState = this.required && !this._value;
    this.stateChanges.next();
    this.onChangeCallback(this._value);
    this._selectionChange.emit();
  }

  onBlur() {
    if (this.required && !this._value) {
      this.errorState = true;
    }
    if (this.onTouched) {
      this.onTouched();
    }
    this.stateChanges.next();
  }

  letters() {
    return this.groupedItems ? Object.keys(this.groupedItems).sort() : [];
  }

  makeGroupedItems() {
    this.groupedItems = {};

    this._items.forEach((item) => {
      if (this.separator) {
        item[this._label] = '';
        this._label.split(this._separator).forEach(label => {
          if (!item[this._label]) {
            item[this._label] = item[label];
            return;
          }
          item[this._label] = item[this._label] + this._separator + item[label];
        });
      }

      const letter = item[this._label].substring(0, 1).toUpperCase();
      if (!this.groupedItems[letter]) {
        this.groupedItems[letter] = [];
      }
      this.groupedItems[letter].push(item);

    });
  }

  searchItem(item) {
    return item[this._label].toLowerCase().includes(this.filter.toLowerCase());
  }

  category(item) {
    switch (this.type) {
      case 'selected':
        return this._value && this._value.find(itemId => itemId === item.id) != null;
      case 'unselected':
        return !this._value || this._value.find(itemId => itemId === item.id) == null;
    }
    return true;
  }

  letterExist(letter) {
    const group = this.groupedItems[letter];
    return group ? group.find(item => this.category(item) && this.searchItem(item)) != null : false;
  }

  isAllSelected() {
    const numSelected = this.value ? this.value.length : -1;
    const numRows = this.items ? this.items.length : -2;
    return numSelected === numRows;
  }


  masterToggleChange() {
    this.isAllSelected()
      ? this.value = []
      : this.value = this.items.map(item => item.id);
    this.selectionChange();

  }


  getFirstSelected(itemId) {
   return  this._items.find(item => item.id === itemId)[this.label];
  }

}
