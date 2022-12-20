import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { AuthenticationService } from "src/app/core/service/authentication.service";
import { DirectionService } from "src/app/core/service/direction.service";
import { PracticeManagementService } from "src/app/main/content/practice management/practice-management/practice-management.service";

@Component({
  selector: "app-main-layout",
  templateUrl: "./main-layout.component.html",
  styleUrls: [],
})
export class MainLayoutComponent implements OnInit {
  direction: string;
  public config: any = {};
  constructor(private directoryService: DirectionService,
              private practiceManagementService: PracticeManagementService,
              private authenticationService: AuthenticationService,
              private sanitizer: DomSanitizer,
  ) {
    this.directoryService.currentData.subscribe((currentData) => {
      if (currentData) {
        this.direction = currentData;
      } else {
        if (localStorage.getItem("isRtl")) {
          if (localStorage.getItem("isRtl") === "true") {
            this.direction = "rtl";
          } else if (localStorage.getItem("isRtl") === "false") {
            this.direction = "ltr";
          }
        } else {
          if (this.config.layout.rtl == true) {
            this.direction = "rtl";
          } else {
            this.direction = "ltr";
          }
        }
      }
    });
  }
  ngOnInit(): void {
  }
}
